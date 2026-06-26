package ru.otus.java.basic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler {
    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;


    public ClientHandler(Socket socket, Server server) throws Exception {
        this.socket = socket;
        this.server = server;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {

                while (true) {
                    String username = in.readUTF();
                    if (server.validateUsername(username)) {
                        setUsername(username);
                        sendMessage("server", "/registerOk");
                        server.broadcastMessage("server", "Подключился пользователь " + getUsername());
                        System.out.println("Подключился пользователь: " + getUsername());
                        break;
                    } else {
                        sendMessage("server", "");
                    }

                }

                while (true) {
                    String message = in.readUTF();
                    if (message.startsWith("/")) {
                        if (message.equals("/exit")) {
                            sendMessage("server", "/exitok");
                            server.broadcastMessage("server", String.format("Пользователь %s отключился", getUsername()));
                            break;
                        }

                        if (message.startsWith("/w")) {
                            String[] split = message.split(" ", 3);
                            String sender = getUsername();
                            String receiver = split[1];
                            message = split[2];
                            server.privateMessage(sender, receiver, message);

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
        if (username == null && message == null) {
            throw new IllegalArgumentException("Некорректные данные");
        }

        try {
            if(username.equals("server")) {
                username = "";
            }
            out.writeUTF(username + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        server.unsubscribe(this);
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
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
