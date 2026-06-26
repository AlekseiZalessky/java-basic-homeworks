package ru.otus.java.basic;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner scanner;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(String host, int port) throws Exception {
        this.scanner = new Scanner(System.in);
        this.socket = new Socket(host, port);
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());

        while (true) {
            System.out.print("Введите имя пользователя: ");
            String username = scanner.nextLine();

            if (username.isBlank()) {
                System.out.println("Имя не может быть пустым");
                continue;
            }
            out.writeUTF(username);

            String response = in.readUTF();
            if (response.equals("/registerOk")) {
                break;
            } else {
                System.out.println("Такое имя занято другим пользователем");
            }
        }

        new Thread(() -> {
            try {
                while (true) {
                    String message = in.readUTF();

                    if (message.startsWith("/")) {
                        if(message.equals("/exitok")){
                            break;
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
                String message = scanner.nextLine();
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
