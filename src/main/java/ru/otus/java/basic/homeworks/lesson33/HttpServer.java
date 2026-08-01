package ru.otus.java.basic.homeworks.lesson33;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private int port;
    private Dispatcher dispatcher;
    private ExecutorService executorService;
    private static final Logger logger = LogManager.getLogger(HttpServer.class);

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Сервер запущен на порту: {}", port);
            logger.info("Ожидаем подключения");
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(() -> {
                    executeRequest(socket);
                });
            }
        } catch (IOException e) {
            logger.error("Ошибка при запуске приложения", e);
        }
    }

    private void executeRequest(Socket socket) {
        try (socket) {
            logger.info("Получено входящее подключение");
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
            logger.error("Произошла ошибка при обработке запроса", e);

        }
    }
}
