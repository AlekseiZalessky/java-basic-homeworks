package ru.otus.java.basic.homeworks.lesson33.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.homeworks.lesson33.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HelloRequestProcessor implements RequestProcessor {
    private static final Logger logger = LogManager.getLogger(HelloRequestProcessor.class);


    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        logger.debug("Обработка запроса /hello");
        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body><h1>Hello World</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
