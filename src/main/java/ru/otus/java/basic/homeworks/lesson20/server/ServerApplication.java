package ru.otus.java.basic.homeworks.lesson20.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ServerApplication {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8899)) {
            System.out.println("Сервер запущен на порту 8899");


            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("Клиент подключился");

                    String operations = "Доступные математические операции: +-*/";
                    socket.getOutputStream().write(operations.getBytes());

                    byte[] bytes = new byte[8192];
                    while (true) {
                        String error = "";
                        int n = socket.getInputStream().read(bytes);
                        String request = new String(bytes, 0, n);

                        if (request.isBlank()) {
                            error = "Введены некорректные данные";
                            socket.getOutputStream().write(error.getBytes(StandardCharsets.UTF_8));
                            break;
                        }

                        String[] requestArray = convertToArray(request);
                        String operation = requestArray[1];
                        System.out.println(Arrays.toString(requestArray));
                        int x = 0, y = 0;

                        try {
                            x = Integer.parseInt(requestArray[0]);
                            y = Integer.parseInt(requestArray[2]);
                        } catch (NumberFormatException e) {
                            error = "Введены некорректные данные";
                            e.printStackTrace();
                        }

                        if (!error.equals("")) {
                            socket.getOutputStream().write(error.getBytes(StandardCharsets.UTF_8));
                        }

                        double result = 0;

                        switch (operation) {
                            case "+":
                                result = x + y;
                                break;
                            case "-":
                                result = x - y;
                                break;
                            case "*":
                                result = x * y;
                                break;
                            case "/":
                                if (y == 0) {
                                    error = "На ноль делить нельзя";
                                    break;
                                }
                                result = (double) x / y;
                                break;
                            default:
                                error = "вы ввели некорректную операцию : '" + operation + "'";
                        }

                        if (!error.equals("")) {
                            socket.getOutputStream().write(error.getBytes(StandardCharsets.UTF_8));
                            break;
                        }

                        String res = String.valueOf(result);
                        socket.getOutputStream().write(res.getBytes(StandardCharsets.UTF_8));

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] convertToArray(String input) {
        char[] array = input.toCharArray();
        boolean flag = false;
        StringBuilder x = new StringBuilder();
        StringBuilder y = new StringBuilder();
        String operation = "";

        for (int i = 0; i < array.length; i++) {

            if (i == 0 && array[i] == '-' ) {
                x.append(array[i]);
                continue;
            }

            if (array[i] == '-' && flag) {
                y.append(array[i]);
                continue;
            }

            if (!String.valueOf(array[i]).matches("[+\\-*/]")) {
                if (!flag) {
                    x.append(array[i]);
                } else {
                    y.append(array[i]);
                }
            } else {
                flag = true;
                operation = String.valueOf(array[i]);
            }
        }

        String[] arrays = new String[3];
        arrays[0] = x.toString().trim();
        arrays[1] = operation;
        arrays[2] = y.toString().trim();

        return arrays;
    }
}
