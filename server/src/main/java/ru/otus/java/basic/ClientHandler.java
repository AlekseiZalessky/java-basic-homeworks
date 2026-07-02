package ru.otus.java.basic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientHandler {
    private final Socket socket;
    private final Server server;
    private final DataInputStream in;
    private final DataOutputStream out;
    private String username;
    private String login;
    private boolean authenticated;
    private Role role;
    private static final String SYSTEM = "system";


    public ClientHandler(Socket socket, Server server) throws Exception {
        this.socket = socket;
        this.server = server;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {
                //аутентификация / регистрация
                while (true) {
                    sendMessage(SYSTEM,
                            "Для начала работы необходимо пройти аутентификацию '/auth login password'\n" +
                                    " или пройти регистрацию '/reg login password username' ");

                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        if (message.equals("/exit")) {
                            sendMessage(SYSTEM, "/exitok");
                            server.broadcastMessage(SYSTEM, String.format("Пользователь %s отключился", getUsername()));
                            break;
                        }
                        if (message.startsWith("/auth ")) {
                            String[] token = message.split(" ");
                            if (token.length != 3) {
                                sendMessage(SYSTEM, "Неверный формат команды /auth");
                                continue;
                            }
                            String login = token[1];
                            String password = token[2];

                            if (server.getAuthenticatedProvider().authenticate(this, login, password)) {
                                authenticated = true;
                                this.login = token[1];
                                server.broadcastMessage(SYSTEM, "Подключился пользователь " + getUsername());
                                break;
                            }
                        }
                        if (message.startsWith("/reg ")) {
                            String[] token = message.split(" ");
                            if (token.length != 4) {
                                sendMessage(SYSTEM, "Неверный формат команды /reg");
                                continue;
                            }

                            String login = token[1];
                            String password = token[2];
                            String username = token[3];

                            if (server.getAuthenticatedProvider().register(this, login, password, username)) {
                                authenticated = true;
                                this.login = token[1];
                                server.broadcastMessage(SYSTEM, "Подключился пользователь " + getUsername());
                                break;
                            }
                        }
                    }
                }

                while (authenticated) {
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        if (message.equals("/exit")) {
                            sendMessage(SYSTEM, "/exitok");
                            server.broadcastMessage(SYSTEM, String.format("Пользователь %s отключился", getUsername()));
                            break;
                        }

                        if (message.startsWith("/w")) {
                            String[] token = message.split(" ", 3);
                            String senderLogin = getLogin();
                            if (token.length != 3) {
                                sendMessage(SYSTEM, "Для отправки приватного сообщения используйте формат '/w username message'");
                                continue;
                            }
                            String receiverUsername = token[1];
                            message = token[2];
                            server.privateMessage(senderLogin, receiverUsername, message);

                        }

                        if (message.startsWith("/kick ")) {
                            if (getRole() != null && !getRole().equals(Role.ADMIN)) {
                                sendMessage(SYSTEM, "У вас нет прав для отклчения пользователей");
                                continue;
                            }

                            String[] token = message.split(" ");
                            if (token.length != 2) {
                                sendMessage(SYSTEM, "Некорректный формат. Используйте команду '/kick username'");
                                continue;
                            }

                            String usernameToKick = token[1];

                            ClientHandler clientHandler = server.getClientByUsername(usernameToKick);
                            if (clientHandler == null) {
                                sendMessage(SYSTEM, String.format("Пользователь %s не найден", usernameToKick));

                            } else {
                                clientHandler.sendMessage(SYSTEM, "/kickok");
                                server.kick(clientHandler);
                            }

                        }
                    } else {
                        server.broadcastMessage(getUsername(), message);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();

    }

    public void sendMessage(String username, String message) {
        if (username == null || message == null) {
            throw new IllegalArgumentException("Некорректные данные");
        }

        try {
            if (username.equals(SYSTEM)) {
                username = "";
            }
            out.writeUTF(username + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {

        if (socket != null) {
            try {
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (in != null) {
            try {
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (out != null) {
            try {
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        server.unsubscribe(this);

    }

    public String getUsername() {
        return username;
    }

    public String getLogin() {
        return login;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }
}
