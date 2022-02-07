package ru.kareev.client.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.kareev.client.ClientChat;
import ru.kareev.client.model.Network;
import ru.kareev.client.model.ReadCommandListener;
import ru.kareev.clientserver.Command;
import ru.kareev.clientserver.CommandType;
import ru.kareev.clientserver.commands.ClientMessageCommandData;
import ru.kareev.clientserver.commands.PrivateMessageCommandData;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class ClientController {

    public static  List<String> USER_TEST_DATA = List.of(
            "username1",
            "username2",
            "username3",
            "username4"
    );

    @FXML private TextArea textArea;
    @FXML private TextField textField;
    @FXML private Button sendButton;
    @FXML public ListView<String> userList;

    private ClientChat application;

    @FXML
    public void initialize() {
        userList.setItems(FXCollections.observableList(USER_TEST_DATA));
    }

    public void sendMessage() {
        String message = textField.getText().trim();

        if (message.isEmpty()){
            textField.clear();
            return;
        }

        String sender = null;
        if (!userList.getSelectionModel().isEmpty()){
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

    private void appendMessageToChat(String sender, String message){
        textArea.appendText(DateFormat.getDateTimeInstance().format(new Date()));
        textArea.appendText(System.lineSeparator());

        if(sender != null){
            textArea.appendText(sender + ": ");
        }
        textArea.appendText(message);
        textArea.appendText(System.lineSeparator());
        textArea.appendText(System.lineSeparator());
        textField.setFocusTraversable(true);
        textField.clear();
    }

    public void setApplication(ClientChat application) {
        this.application = application;
    }

    public void initializeMessageHandler() {
        Network.getInstance().addReadMessageListener(new ReadCommandListener() {
            @Override
            public void processReceivedCommand(Command command) {
                if (command.getType() == CommandType.CLIENT_MESSAGE) {
                    ClientMessageCommandData data = (ClientMessageCommandData) command.getData();
                    appendMessageToChat(data.getSender(), data.getMessage());
                }
            }
        });
    }
}