package ru.otus.java.basic.auth;

import ru.otus.java.basic.ClientHandler;
import ru.otus.java.basic.Role;
import ru.otus.java.basic.Server;

import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.otus.java.basic.Commands.*;

public class InMemoryAuthenticatedProvider implements AuthenticatedProvider {

    private class User {
        public String login;
        public String password;
        public String username;
        public Role role;

        public User(String login, String password, String username) {
            this.login = login;
            this.password = password;
            this.username = username;

            if (login.equals("admin")) {
                this.role = Role.ADMIN;
            } else {
                this.role = Role.USER;
            }
        }

    }

    private final Map<String, User> users;
    private final Server server;
    private static final String SYSTEM = "system";

    public InMemoryAuthenticatedProvider(Server server) {
        this.server = server;
        users = new ConcurrentHashMap<>();
        users.put("admin", new User("admin", "admin", "admin"));
        users.put("user1", new User("user1", "user1", "user1"));
        users.put("user2", new User("user2", "user2", "user2"));
    }

    @Override
    public void init() {
        System.out.println("Сервис аутентификации запущен в режиме InMemory");
    }

    @Override
    public boolean authenticate(ClientHandler clientHandler, String login, String password) {

        User user = users.get(login);

        if (user == null || !user.password.equals(password)) {
            clientHandler.sendMessage(SYSTEM, "Неверный логин/пароль");
            return false;
        }

        String authUsername = user.username;

        if (server.isLoginBusy(login)) {
            clientHandler.sendMessage(SYSTEM, "Данная учетная запись уже используется");
            return false;
        }

        clientHandler.setUsername(authUsername);
        clientHandler.setRole(user.role);
        server.subscribe(clientHandler, login);
        clientHandler.sendMessage(SYSTEM, "/authok " + login);

        return true;
    }

    @Override
    public boolean register(ClientHandler clientHandler, String login, String password, String username) {

        if (login.length() < 3) {
            clientHandler.sendMessage(SYSTEM, "Длина логина не может быть короче трех символов");
            return false;
        }

        if (password.length() < 3) {
            clientHandler.sendMessage(SYSTEM, "Длина пароля не может быть короче трех символов");
            return false;
        }

        if (username.length() < 3) {
            clientHandler.sendMessage(SYSTEM, "Длина имени пользователя не может быть короче трех символов");
            return false;
        }

        if (users.containsKey(login)) {
            clientHandler.sendMessage(SYSTEM, "Указанный логин занят");
            return false;
        }

        if (isUsernameAlreadyExists(username)) {
            clientHandler.sendMessage(SYSTEM, "Указанное имя пользователя занято");
            return false;
        }

        users.put(login, new User(login, password, username));
        clientHandler.setUsername(username);
        clientHandler.setRole(Role.USER);
        server.subscribe(clientHandler, login);
        clientHandler.sendMessage(SYSTEM, "/regok " + login);

        return true;
    }

    private boolean isUsernameAlreadyExists(String username) {
        for (Map.Entry<String, User> entry : users.entrySet()) {
            if (entry.getValue().username.equals(username)) {
                return true;
            }
        }
        return false;
    }
}
