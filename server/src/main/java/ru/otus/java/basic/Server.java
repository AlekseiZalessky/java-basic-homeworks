package ru.otus.java.basic;

import ru.otus.java.basic.auth.AuthenticatedProvider;
import ru.otus.java.basic.auth.InMemoryAuthenticatedProvider;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ru.otus.java.basic.Commands.*;

public class Server {
    private final int port;
    private final Map<String, ClientHandler> clients;
    private final AuthenticatedProvider authenticatedProvider;


    public Server(int port) {
        this.port = port;
        this.clients = new ConcurrentHashMap<>();
        this.authenticatedProvider = new InMemoryAuthenticatedProvider(this);
        authenticatedProvider.init();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void subscribe(ClientHandler client, String login) {
        clients.put(login, client);
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client.getLogin());

        if (client.getUsername() != null) {
            broadcastMessage(SYSTEM, String.format("Пользователь %s отключился", client.getUsername()));
        }

        client.setAuthenticated(false);
    }

    public void kick(ClientHandler client) {
        clients.remove(client.getLogin());
        if (client.getUsername() != null) {
            broadcastMessage(SYSTEM, String.format("Пользователь %s был отключен администратором", client.getUsername()));
        }
        client.setAuthenticated(false);
    }

    public void broadcastMessage(String username, String message) {
        if (username == null || message == null) {
            throw new IllegalArgumentException("Некорректные входные данные");
        }

        if (SYSTEM.equals(username)) {
            username = "";
        } else {
            username += " -> всем: ";
        }

        for (Map.Entry<String, ClientHandler> entry : clients.entrySet()) {
            entry.getValue().sendMessage(username, message);
        }
    }

    public ClientHandler getClientByUsername(String username) {
        for (Map.Entry<String, ClientHandler> entry : clients.entrySet()) {
            if(entry.getValue().getUsername().equals(username)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public void privateMessage(String senderLogin, String receiverUsername, String message) {
        if (senderLogin == null || receiverUsername == null || message == null) {
            throw new IllegalArgumentException("Некорректные входные данные");
        }

        ClientHandler sender = clients.get(senderLogin);
        ClientHandler receiver = getClientByUsername(receiverUsername);

        if (receiver == null) {
            sender.sendMessage(SYSTEM, String.format("Пользователь %s не найден", receiverUsername));
            return;
        }

        if (senderLogin.equals(receiver.getLogin())) {
            sender.sendMessage(SYSTEM, "Вы не можете отправлять сообщение самому себе");
            return;
        }

        receiver.sendMessage(sender.getUsername() + " -> вам: ", message);
        sender.sendMessage("Вы -> " + receiverUsername + ": ", message);

    }

    public boolean isLoginBusy(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Логин не может быть пустымм");
        }

        return clients.containsKey(login);
    }


    public AuthenticatedProvider getAuthenticatedProvider() {
        return authenticatedProvider;
    }
}
