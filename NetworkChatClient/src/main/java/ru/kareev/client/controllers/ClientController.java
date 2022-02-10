package ru.kareev.client.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.kareev.client.ClientChat;
import ru.kareev.client.model.Network;
import ru.kareev.client.model.ReadCommandListener;
import ru.kareev.clientserver.Command;
import ru.kareev.clientserver.CommandType;
import ru.kareev.clientserver.commands.ClientMessageCommandData;
import ru.kareev.clientserver.commands.UpdateUserListCommandData;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;

public class ClientController {

    @FXML
    private TextArea textArea;
    @FXML
    private TextField textField;
    @FXML
    private Button sendButton;
    @FXML
    public ListView<String> userList;

    private ClientChat application;
    private String time = DateFormat.getDateTimeInstance().format(new Date());
    private String username;

    public void sendMessage() {
        String message = textField.getText().trim();

        if (message.isEmpty()) {
            textField.clear();
            return;
        }

        String sender = null;
        if (!userList.getSelectionModel().isEmpty()) {
            sender = userList.getSelectionModel().getSelectedItem();
        }

        try {
            if (sender != null) {
                Network.getInstance().sendPrivateMessage(sender, message);
            } else {
                Network.getInstance().sendMessage(message);
            }
        } catch (IOException e) {
            application.showErrorDialog("Ошибка передачи данных по сети");
        }

        appendMessageToChat("Я", message);
    }

    private void appendMessageToChat(String sender, String message) {
        textArea.appendText(time);
        textArea.appendText(System.lineSeparator());

        if (sender != null) {
            textArea.appendText(sender + ": ");
            textArea.appendText(System.lineSeparator());
            safeHistory(sender, message);
        }
        textArea.appendText(message);
        textArea.appendText(System.lineSeparator());
        textArea.appendText(System.lineSeparator());
        textField.setFocusTraversable(true);
        textField.clear();
    }

    private void safeHistory(String sender, String message) {
        String m = (time + " " + sender + " " + message + "\n");
        File file = new File(username + ".txt");
        try (FileOutputStream fileOutputStream = new FileOutputStream(username + ".txt", file.exists())) {
           fileOutputStream.write(m.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setApplication(ClientChat application) {
        this.application = application;
    }

    public void initializeMessageHandler(String username) {
        this.username = username;
        Network.getInstance().addReadMessageListener(new ReadCommandListener() {
            @Override
            public void processReceivedCommand(Command command) {
                if (command.getType() == CommandType.CLIENT_MESSAGE) {
                    ClientMessageCommandData data = (ClientMessageCommandData) command.getData();
                    appendMessageToChat(data.getSender(), data.getMessage());
                } else if (command.getType() == CommandType.UPDATE_USER_LIST) {
                    UpdateUserListCommandData data = (UpdateUserListCommandData) command.getData();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            userList.setItems(FXCollections.observableList(data.getUsers()));
                        }
                    });
                }
            }
        });
    }
}