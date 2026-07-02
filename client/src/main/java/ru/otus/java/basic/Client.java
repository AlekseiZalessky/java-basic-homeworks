package ru.otus.java.basic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    private final Scanner scanner;
    private final DataInputStream in;
    private final DataOutputStream out;
    private boolean isKicked;

    public Client(String host, int port) throws Exception {
        this.scanner = new Scanner(System.in);
        this.socket = new Socket(host, port);
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {
                while (true) {
                    String message = in.readUTF();

                    if (message.startsWith("/")) {

                        if (message.equals("/exitok")) {
                            break;
                        }
                        if (message.startsWith("/authok")) {
                            String login = message.split(" ")[1];
                            System.out.println("Вы успешно аутентифицировались под логином: " + login);
                        }
                        if(message.startsWith("/regok")) {
                            String login = message.split(" ")[1];
                            System.out.println("Вы успешно зарегистрировались и вошли под логином: " + login);
                        }

                        if(message.equals("/kickok")) {
                            System.out.println("Вы отключены администратором");
                            isKicked = true;
                            return;
                        }

                    } else {
                        System.out.println(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();


        try {
            while (true) {

                if(isKicked) {
                    break;
                }

                String message = scanner.nextLine();
                if (message.isBlank()) {
                    System.out.println("Соббщение не может быть пустым");
                    continue;
                }

                out.writeUTF(message);
                if (message.equals("/exit")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        scanner.close();
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
}
