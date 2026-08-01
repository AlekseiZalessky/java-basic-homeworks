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

public class GetItemByIdProcessor implements RequestProcessor {
    private static final Logger logger = LogManager.getLogger(GetItemByIdProcessor.class);
    private ItemsService itemsService;

    public GetItemByIdProcessor(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {

        String result;
        Gson gson = new Gson();
        Long id;

        String uri = request.getUri();
        String[] parts = uri.split("/");
        String inputId = parts[parts.length - 1];

        try {
            id = Long.parseLong(inputId);
            ItemDto itemDto = itemsService.getById(id);
            if(itemDto != null) {
                logger.debug("Айтем с id: {}: {}", id, itemDto);
                result = gson.toJson(itemDto);
            } else {
                logger.debug("Айтем с id: {} не найден", id);
                throw new BusinessLogicException("ITEM_NOT_FOUND", "Айтем с ID " + id + " не найден");
            }
        } catch (NumberFormatException e) {
            logger.error("Некорректный id: {}", inputId);
            throw new BusinessLogicException("INVALID_INPUT_DATA", "id должен быть числом");
        }

        logger.debug("результат запроса: {}", result);
        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                result;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
