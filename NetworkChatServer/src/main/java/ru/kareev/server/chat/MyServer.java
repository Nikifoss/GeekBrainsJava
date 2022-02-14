package ru.kareev.server.chat;

import ru.kareev.clientserver.Command;
import ru.kareev.clientserver.sqlService.SQLFunction;
import ru.kareev.server.chat.auth.AuthService;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {

    private final List<ClientHandler> clients = new ArrayList<>();
    private AuthService authService;

    public void start(int port){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server has been started");
            authService = new AuthService();

            while (true) {
                waitAndProcessClientConnections(serverSocket);
            }

        } catch (IOException e){
            System.err.println("Failed to bind port " + port);
            e.printStackTrace();
        }
    }

    private void waitAndProcessClientConnections(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client has been connected");
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.handle();
    }

    public synchronized void broadcastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            if(client != sender){
            client.sendCommand(Command.clientMessageCommand(sender.getUserName(), message));
             }
        }
    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String recipient, String privateMessage) throws IOException {
        for (ClientHandler client : clients) {
            if(client != sender && client.getUserName().equals(recipient)) {
                client.sendCommand(Command.clientMessageCommand(sender.getUserName(), privateMessage));
                break;
            }
        }
    }

    public synchronized boolean isUserNameBusy(String username) {
        for (ClientHandler client : clients) {
            if(client.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        this.clients.add(clientHandler);
        notifyClientUserListUpdated();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {
        this.clients.remove(clientHandler);
        notifyClientUserListUpdated();
    }

    private void notifyClientUserListUpdated() throws IOException {
        List<String> userListOnline = new ArrayList<>();

        for (ClientHandler client : clients) {
            userListOnline.add(client.getUserName());
        }

        for (ClientHandler client : clients) {
            client.sendCommand(Command.updateUserListCommand(userListOnline));
            System.out.println(userListOnline);
        }
    }

    public AuthService getAuthService() {
        return authService;
    }
}
