package ru.otus.java.basic.homeworks.lesson32;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private Dispatcher dispatcher;
    private ExecutorService executor;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.executor = Executors.newFixedThreadPool(3);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            System.out.println("Сервер запущен на порту " + port);

            while (true) {
                Socket socket = serverSocket.accept();

                executor.execute(() -> {
                    try (Socket s = socket) {
                        System.out.println("Получено подключение");
                        byte[] buffer = new byte[8192];
                        int n = s.getInputStream().read(buffer);
                        if (n < 0) {
                            return;
                        }
                        String rawRequest = new String(buffer, 0, n);
                        HttpRequest request = new HttpRequest(rawRequest);
                        request.info(true);
                        dispatcher.execute(request, s.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
