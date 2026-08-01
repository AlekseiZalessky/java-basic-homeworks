package ru.otus.java.basic.homeworks.lesson33.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.homeworks.lesson33.HttpRequest;
import ru.otus.java.basic.homeworks.lesson33.app.ItemsService;
import ru.otus.java.basic.homeworks.lesson33.app.dto.ItemDto;
import ru.otus.java.basic.homeworks.lesson33.errors_handling.BusinessLogicException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetItemsProcessor implements RequestProcessor {
    private static final Logger logger = LogManager.getLogger(GetItemsProcessor.class);
    private ItemsService itemsService;

    public GetItemsProcessor(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {

        String result = null;
        Gson gson = new Gson();

        logger.debug("Запрос получения всем айтемов");
        result = gson.toJson(itemsService.getAll());

        logger.debug("результат запроса: {}", result);
        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                result;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
