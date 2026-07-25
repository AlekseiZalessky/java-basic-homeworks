package ru.otus.java.basic.homeworks.lesson32;

import ru.otus.java.basic.homeworks.lesson32.processors.AddRequestProcessor;
import ru.otus.java.basic.homeworks.lesson32.processors.DefaultNotFoundRequestProcessor;
import ru.otus.java.basic.homeworks.lesson32.processors.GetItemsProcessor;
import ru.otus.java.basic.homeworks.lesson32.processors.HelloRequestProcessor;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> processors;
    private RequestProcessor defaultNotFoundProcessor;

    public Dispatcher() {
        this.processors = new HashMap<>();
        processors.put("/add", new AddRequestProcessor());
        processors.put("/hello", new HelloRequestProcessor());
        processors.put("/item", new GetItemsProcessor());
        this.defaultNotFoundProcessor = new DefaultNotFoundRequestProcessor();
    }

    public void execute(HttpRequest request, OutputStream output) throws IOException {
        if (!processors.containsKey(request.getUri())) {
            defaultNotFoundProcessor.execute(request, output);
            return;
        }
        processors.get(request.getUri()).execute(request, output);
    }
}
