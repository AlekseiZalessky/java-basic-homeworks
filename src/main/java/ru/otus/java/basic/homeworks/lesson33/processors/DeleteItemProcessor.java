package ru.otus.java.basic.homeworks.lesson33.processors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.homeworks.lesson33.HttpRequest;
import ru.otus.java.basic.homeworks.lesson33.app.ItemsService;
import ru.otus.java.basic.homeworks.lesson33.app.dto.ItemDto;
import ru.otus.java.basic.homeworks.lesson33.errors_handling.BusinessLogicException;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DeleteItemProcessor implements RequestProcessor {
    private static final Logger logger = LogManager.getLogger(DeleteItemProcessor.class);
    private ItemsService itemsService;

    public DeleteItemProcessor(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        logger.info("Delete Item");
        try{
            String uri = request.getUri();
            Long id = Long.parseLong(uri.split("/")[uri.split("/").length - 1]);
            ItemDto item = itemsService.deleteItem(id);
            if (item != null) {
                logger.debug("айтем с id {} был удален", id);
                String response = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: application/json\r\n" +
                        "\r\n" +
                        "{\"status\":\"deleted\",\"id\":" + id + "}";
                output.write(response.getBytes(StandardCharsets.UTF_8));

            } else {
                logger.debug("Айтем с id: {} не найден", id);
                throw new BusinessLogicException("ITEM_NOT_FOUND", "Айтем с ID " + id + " не найден");
            }
        } catch (NumberFormatException e) {
            logger.error("Некорректный id");
            throw new BusinessLogicException("INVALID_INPUT_DATA", "id должен быть числом");
        }
    }
}
