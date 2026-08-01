package ru.otus.java.basic.homeworks.lesson33.processors;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.homeworks.lesson33.HttpRequest;
import ru.otus.java.basic.homeworks.lesson33.errors_handling.BusinessLogicException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class AddRequestProcessor implements RequestProcessor {
    private static final Logger logger = LogManager.getLogger(AddRequestProcessor.class);

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {

        if (!request.containsParam("a")) {
            logger.error("Отсутствует обязательный параметр 'a'");
            throw new BusinessLogicException("INVALID_INPUT_DATA", "Отсутствует обязательный параметр 'a'");
        }
        if (!request.containsParam("b")) {
            logger.error("Отсутствует обязательный параметр 'b'");
            throw new BusinessLogicException("INVALID_INPUT_DATA", "Отсутствует обязательный параметр 'b'");
        }

        logger.debug("Входные параметры: a= {}, b={}", request.getParam("a"), request.getParam("b"));

        int a, b;

        try {
             a = Integer.parseInt(request.getParam("a"));
             b = Integer.parseInt(request.getParam("b"));
        } catch (NumberFormatException e) {
            logger.error("Некорректные входные данные: a={}, b={}", request.getParam("a"), request.getParam("b"));
            throw new BusinessLogicException("INVALID_INPUT_DATA", "a и b должны быть числами");
        }

        String result = a + " + " + b + " = " + (a + b);

        logger.info("Результат сложения: {}", result);

        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: text/html\r\n" +
                "\r\n" +
                "<html><body><h1>" + result + "</h1></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));

        logger.debug("Ответ отправлен клиенту");
    }
}
