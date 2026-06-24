package ru.otus.java.basic.homeworks.lesson20.client;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try (Socket socket = new Socket("localhost", 8899)) {
            byte[] operation = new byte[256];
            int n = socket.getInputStream().read(operation);
            System.out.println(new String(operation, 0, n));

            byte[] buffer = new byte[8192];

            while (true) {
                System.out.println("\nДля выхода напишите '/exit'");
                System.out.println("Введите необходимое действие в виде '5+5'");
                String message = scanner.nextLine();
                if (message.equals("/exit")) {
                    break;
                }
                socket.getOutputStream().write(message.getBytes(StandardCharsets.UTF_8));

                n = socket.getInputStream().read(buffer);
                String response = new String(buffer, 0, n);
                System.out.println(response);
                if (response.equalsIgnoreCase("/exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
