package ru.otus.java.basic.homeworks.lesson32;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private int port;
    private Dispatcher dispatcher;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    public void start(){
        try( ServerSocket serverSocket = new ServerSocket(port);){

            System.out.println("Server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();

                new Thread(() -> {
                    try (Socket s = socket) {
                        System.out.println("Получено подключение");
                        byte[] buffer = new byte[8192];
                        int n = s.getInputStream().read(buffer);
                        String rawRequest = new String(buffer, 0, n);
                        HttpRequest request = new HttpRequest(rawRequest);
                        request.info(true);
                        dispatcher.execute(request, s.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
