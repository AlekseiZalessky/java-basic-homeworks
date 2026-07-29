package ru.otus.java.basic.homeworks.lesson33;

import org.apache.logging.log4j.LogManager;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class HttpRequest {
    private String rawRequest;
    private HttpMethod httpMethod;
    private String uri;
    private String body;
    private Map<String, String> params;
    private Map<String, String> headers;
    private static final Logger logger = LogManager.getLogger(HttpRequest.class);

    public String getBody() {
        return body;
    }

    public String getUri() {
        return uri;
    }

    public String getRoutingKey() {
        return httpMethod + " " + uri;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public boolean containsParam(String key) {
        return params.containsKey(key);
    }

    public boolean containsHeader(String key) { return headers.containsKey(key); }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpRequest(String rawRequest) {
        this.params = new HashMap<>();
        this.headers = new HashMap<>();
        this.rawRequest = rawRequest;
        this.parse();
    }

    public void info(boolean showRawRequest) {
        if (showRawRequest) {
            System.out.println(rawRequest);
        }
        System.out.println("HTTP METHOD: " + httpMethod);
        System.out.println("URI: " + uri);
        System.out.println("PARAMS: " + params);
        System.out.println("HEADERS: " + headers);
    }

    private void parse() {
        logger.debug("Парсинг rawRequest: {}", rawRequest );
        int start = rawRequest.indexOf(' ');
        int end = rawRequest.indexOf(' ', start + 1);
        httpMethod = HttpMethod.valueOf(rawRequest.substring(0, start));
        uri = rawRequest.substring(start + 1, end);
        if (uri.contains("?")) {
            String[] tokens = uri.split("[?]");
            uri = tokens[0];
            String[] keysValues = tokens[1].split("&");
            for (String o : keysValues) {
                String[] keyValue = o.split("=", 2);
                params.put(keyValue[0], keyValue[1]);
            }
        }
        if (httpMethod == HttpMethod.POST) {
            body = rawRequest.substring(rawRequest.indexOf("\r\n\r\n") + 4);
        }

        logger.debug("результат парсинга параметров: {}", params);

        // парсинг заголовков
        String[] requestHeaders = rawRequest.substring(rawRequest.indexOf("\r\n"), rawRequest.indexOf("\r\n\r\n")).split("\r\n");

        for (String h : requestHeaders) {
            if(h.isEmpty()) continue;
            String[] keyValue = h.split(": ");
            if (keyValue.length == 2) {
                headers.put(keyValue[0], keyValue[1]);
            }
        }

        logger.debug("результат парсинга заголовков: {}", headers);
    }
}
