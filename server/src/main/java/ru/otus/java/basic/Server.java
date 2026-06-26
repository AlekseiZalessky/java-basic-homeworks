package ru.otus.java.basic;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private int port;
    private List<ClientHandler> clients;

    public Server(int port) {
        this.port = port;
        this.clients = new CopyOnWriteArrayList<ClientHandler>();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                subscribe(new ClientHandler(socket, this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        if (client.getUsername() != null) {
            broadcastMessage("server", String.format("Пользователь %s отключился", client.getUsername()));
            clients.remove(client);
        }
    }

    public void broadcastMessage(String username, String message) {
        if (username == null || message == null) {
            throw new IllegalArgumentException("Некорректные входные данные");
        }

        for (ClientHandler client : clients) {
            if ("server".equals(username)) {
                client.sendMessage("", message);
            } else {
                client.sendMessage(username + " -> всем: ", message);
            }
        }
    }

    public void privateMessage(String sender, String receiver, String message) {
        if (sender == null || receiver == null || message == null) {
            throw new IllegalArgumentException("Некорректные входные данные");
        }

        if (sender.equals(receiver)) {
            for (ClientHandler client : clients) {
                if (sender.equals(client.getUsername())) {
                    client.sendMessage("server", "Вы не можете отправлять сообщение самому себе");
                }
            }
        } else {
            boolean isReceiver = false;

            for (ClientHandler client : clients) {
                if (receiver.equals(client.getUsername())) {
                    isReceiver = true;
                }
            }

            if (isReceiver) {
                for (ClientHandler client : clients) {
                    if (client.getUsername().equals(receiver)) {
                        client.sendMessage(sender + " -> вам: ", message);
                    }
                    if (client.getUsername().equals(sender)) {
                        client.sendMessage("Вы -> " + receiver + ": ", message);
                    }
                }
            } else {
                for (ClientHandler client : clients) {
                    if (sender.equals(client.getUsername())) {
                        client.sendMessage("server", String.format("Пользователь %s не найден", receiver));
                    }
                }
            }
        }
    }

    public boolean validateUsername(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }

        for (ClientHandler client : clients) {
            if (client.getUsername() != null && client.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }
}
