package ru.otus.java.basic.homeworks.lesson33.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.homeworks.lesson33.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DefaultNotFoundRequestProcessor implements RequestProcessor {
    private static final Logger logger = LogManager.getLogger(DefaultNotFoundRequestProcessor.class);
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        logger.debug("Входящий uri: '{}'", request.getUri());
        String response = "" +
                "HTTP/1.1 404 Not Found\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body><h1>Page Not Found</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
        logger.debug("Ответ отправлен клиенту");
    }
}
