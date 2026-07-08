package ru.otus.java.basic.auth;

import ru.otus.java.basic.ClientHandler;
import ru.otus.java.basic.Role;
import ru.otus.java.basic.Server;

import java.sql.*;

import static ru.otus.java.basic.Commands.*;

public class InDatabaseAuthenticatedProvider implements AuthenticatedProvider {

    private final Server server;
    private final Connection connection;

    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "password";
    private static final String DB_URL = "jdbc:postgresql://localhost:5433/otus-db";


    public InDatabaseAuthenticatedProvider(Server server) throws SQLException {
        this.server = server;
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    @Override
    public void init() {
        System.out.println("Сервис аутентификации запущен в режиме InDatabase");
    }

    @Override
    public boolean authenticate(ClientHandler clientHandler, String login, String password) {

        try (PreparedStatement prst = connection.prepareStatement("select * from users where login=?")) {
            prst.setString(1, login);
            try (ResultSet rs = prst.executeQuery()) {
                if (!rs.next() || !rs.getString("password").equals(password)) {
                    clientHandler.sendMessage(SYSTEM, "Неверный логин/пароль");
                    return false;
                }

                if (server.isLoginBusy(login)) {
                    clientHandler.sendMessage(SYSTEM, "Данная учетная запись уже используется");
                    return false;
                }
                String authUsername = rs.getString("username");
                String role = rs.getString("role");

                clientHandler.setUsername(authUsername);
                clientHandler.setRole(Role.valueOf(role));
                server.subscribe(clientHandler, login);
                clientHandler.sendMessage(SYSTEM, AUTH_OK + " " + login);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

        try  {

            try (PreparedStatement prst = connection.prepareStatement("select * from users where login=?")) {
                prst.setString(1, login);
                try (ResultSet rs = prst.executeQuery()) {
                    if (rs.next()) {
                        clientHandler.sendMessage(SYSTEM, "Указанный логин занят");
                        return false;
                    }
                }
            }


            try (PreparedStatement prst = connection.prepareStatement("select * from users where username=?")) {
                prst.setString(1, username);
                try (ResultSet rs = prst.executeQuery()) {
                    if (rs.next()) {
                        clientHandler.sendMessage(SYSTEM, "Указанное имя пользователя занято");
                        return false;
                    }
                }
            }

            try (PreparedStatement prst = connection.prepareStatement("insert into users (login, password, username, role) values (?, ?, ?, ?)")) {

                prst.setString(1, login);
                prst.setString(2, password);
                prst.setString(3, username);
                prst.setString(4, String.valueOf(Role.USER));

                int res = prst.executeUpdate();

                if (res > 0) {
                    clientHandler.setUsername(username);
                    clientHandler.setRole(Role.USER);
                    server.subscribe(clientHandler, login);
                    clientHandler.sendMessage(SYSTEM, REG_OK + " " + login);
                    return true;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
