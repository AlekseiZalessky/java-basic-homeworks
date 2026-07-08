package ru.otus.java.basic;

import java.sql.SQLException;

public class ServerApplication {
    public static void main(String[] args) throws SQLException {
        int port = 8189;
        new Server(port).start();
    }
}
