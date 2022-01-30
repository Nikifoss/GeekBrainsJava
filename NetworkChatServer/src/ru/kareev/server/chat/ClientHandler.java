package ru.kareev.server.chat;

import ru.kareev.clientserver.Command;
import ru.kareev.clientserver.CommandType;
import ru.kareev.clientserver.commands.AuthCommandData;
import ru.kareev.clientserver.commands.PrivateMessageCommandData;
import ru.kareev.clientserver.commands.PublicMessageCommandData;
import ru.kareev.server.chat.auth.AuthService;

import java.io.*;
import java.net.Socket;

public class ClientHandler {

    private final MyServer server;
    private final Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    private String username;

    public ClientHandler(MyServer myServer, Socket clientSocket) {
        this.server = myServer;
        this.clientSocket = clientSocket;

    }

    public void handle() throws IOException {
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        inputStream = new ObjectInputStream(clientSocket.getInputStream());

        new Thread(() -> {
            try {
                authenticate();
                readMessages();
            } catch (EOFException e){
                System.err.println("Connection closed");
            }catch (IOException e) {
                System.err.println("Failed to process message from client");
                e.printStackTrace();
            } finally {
                try {
                    closeConnection();
                } catch (IOException e) {
                    System.err.println("Failed to close connection");
                }
            }
        }).start();
    }

    private void authenticate() throws IOException {
        while (true) {
            Command command = readCommand();
            if(command == null) {
                continue;
            }

            if(command.getType() == CommandType.AUTH) {
                AuthCommandData data = (AuthCommandData) command.getData();
                String login = data.getLogin();
                String password = data.getPassword();

                String userName = server.getAuthService().getUserNameByLoginAndPassword(login, password);

                if(userName == null){
                    sendCommand(Command.errorCommand("Некорректные логин и пароль"));
                } else if (server.isUserNameBusy(userName)) {
                    sendCommand(Command.errorCommand("Такой пользователь уже существует"));
                } else {
                    this.username = userName;
                    sendCommand(Command.authOkCommand(userName));
                    server.subscribe(this);
                    return;
                }
            }
        }
    }

    public void sendCommand(Command command) throws IOException {
        outputStream.writeObject(command);
    }

    private Command readCommand() throws IOException {
        Command command = null;
        try {
            command = (Command) inputStream.readObject();
        }  catch (ClassNotFoundException e) {
            System.err.println("Failed to read command class ");
            e.printStackTrace();
        }

        return command;
    }

    private void readMessages() throws IOException {
        while (true){
            Command command = readCommand();

            if(command == null) {
                continue;
            }

            switch (command.getType()) {
                case END:
                    return;
                case PRIVATE_MESSAGE: {
                    PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                    String recipient = data.getReceiver();
                    String privateMessage = data.getMessage();
                    server.sendPrivateMessage(this, recipient, privateMessage);
                    break;
                }
                case PUBLIC_MESSAGE: {
                    PublicMessageCommandData data = (PublicMessageCommandData) command.getData();
                    processMessage(data.getMessage());
                }
            }
        }
    }

    private void processMessage(String message) throws IOException {
        this.server.broadcastMessage(message , this);
    }

    private void closeConnection() throws IOException {
        server.unsubscribe(this);
        clientSocket.close();
    }

    public String getUsername() {
        return username;
    }
}
