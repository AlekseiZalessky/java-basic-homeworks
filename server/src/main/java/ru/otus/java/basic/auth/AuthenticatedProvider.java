package ru.otus.java.basic.auth;

import ru.otus.java.basic.ClientHandler;

public interface AuthenticatedProvider {
    void init();
    boolean authenticate(ClientHandler clientHandler, String login, String password);
    boolean register(ClientHandler clientHandler, String login, String password, String username);
}
