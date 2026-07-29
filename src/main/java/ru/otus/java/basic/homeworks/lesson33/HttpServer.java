package ru.otus.java.basic.homeworks.lesson33;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private Dispatcher dispatcher;
    private ExecutorService executorService;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту: " + port);
            System.out.println("Ожидаем подключения");
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> {
                    executeRequest(socket);
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void executeRequest(Socket socket) {
        try (socket) {
            System.out.println("Получено входящее подключение");
            byte[] buffer = new byte[8192];
            int n = socket.getInputStream().read(buffer);
            if (n < 0) {
                return;
            }
            String rawRequest = new String(buffer, 0, n);
            HttpRequest request = new HttpRequest(rawRequest);
            request.info(true);
            dispatcher.execute(request, socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
