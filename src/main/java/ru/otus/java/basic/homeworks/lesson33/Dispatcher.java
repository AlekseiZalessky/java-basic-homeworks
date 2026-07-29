package ru.otus.java.basic.homeworks.lesson33;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.homeworks.lesson33.app.ItemsService;
import ru.otus.java.basic.homeworks.lesson33.errors_handling.BusinessLogicException;
import ru.otus.java.basic.homeworks.lesson33.errors_handling.ErrorDto;
import ru.otus.java.basic.homeworks.lesson33.processors.*;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> processors;
    private RequestProcessor defaultNotFoundRequestProcessor;
    private RequestProcessor defaultStaticResourceRequestProcessor;
    private static final Logger logger = LogManager.getLogger(Dispatcher.class);

    public Dispatcher() {
        ItemsService itemsService = new ItemsService();
        this.processors = new HashMap<>();
        this.processors.put("GET /add", new AddRequestProcessor());
        this.processors.put("GET /hello", new HelloRequestProcessor());
        this.processors.put("GET /items", new GetItemsProcessor(itemsService));
        this.processors.put("GET /items/*", new GetItemByIdProcessor(itemsService));
        this.processors.put("POST /items", new CreateItemProcessor(itemsService));
        this.processors.put("DELETE /items/*", new DeleteItemProcessor(itemsService));
        this.defaultNotFoundRequestProcessor = new DefaultNotFoundRequestProcessor();
        this.defaultStaticResourceRequestProcessor = new DefaultStaticResourceProcessor();

        logger.info("Dispatcher инициализирован");
    }

    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String routingKey = request.getRoutingKey();
        logger.debug("Обработка запроса: {}", routingKey);
        if (Files.exists(Path.of("static", request.getUri().substring(1)))) {
            defaultStaticResourceRequestProcessor.execute(request, output);
            return;
        }

        String[] parts = routingKey.split("/");
        if (parts.length > 2) {
            routingKey = routingKey.substring(0, routingKey.lastIndexOf("/") + 1) + "*";
        }

        if (!processors.containsKey(routingKey)) {
            defaultNotFoundRequestProcessor.execute(request, output);
            return;
        }
        try {
            processors.get(routingKey).execute(request, output);
        } catch (BusinessLogicException e) {
            Gson gson = new Gson();
            ErrorDto errorDto = new ErrorDto(e.getCode(), e.getDescription());
            String response = "" +
                    "HTTP/1.1 400 Bad Request\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" + gson.toJson(errorDto);
            output.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            Gson gson = new Gson();
            ErrorDto errorDto = new ErrorDto("UNEXPECTED_CRITICAL_ERROR", "Произошла непредвиденная ошибка при обработке запроса");
            String response = "" +
                    "HTTP/1.1 500 Internal Server Error\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" + gson.toJson(errorDto);
            output.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
