package ru.otus.java.basic;

public class ServerApplication {
    public static void main(String[] args) {
        int port = 8189;
        new Server(port).start();
    }
}
