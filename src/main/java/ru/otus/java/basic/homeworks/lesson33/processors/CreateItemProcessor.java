package ru.otus.java.basic.homeworks.lesson33.processors;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.homeworks.lesson33.HttpRequest;
import ru.otus.java.basic.homeworks.lesson33.app.ItemsService;
import ru.otus.java.basic.homeworks.lesson33.app.dto.ItemDto;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateItemProcessor implements RequestProcessor {
    private static final Logger logger = LogManager.getLogger(CreateItemProcessor.class);
    private ItemsService itemsService;

    public CreateItemProcessor(ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        logger.debug("Входные параметры: {}", request.getBody());
        Gson gson = new Gson();
        ItemDto itemDto = itemsService.createNewItem(gson.fromJson(request.getBody(), ItemDto.class));
        logger.info("Создан новый айтем: {}", itemDto);
        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                gson.toJson(itemDto);
        output.write(response.getBytes(StandardCharsets.UTF_8));

        logger.debug("Ответ отправлен клиенту");
    }
}
