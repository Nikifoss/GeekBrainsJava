package ru.kareev.client.controllers;

import javafx.application.Platform;
import ru.kareev.client.ClientChat;
import ru.kareev.client.dialogs.Dialogs;
import ru.kareev.client.model.Network;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.kareev.client.model.ReadCommandListener;
import ru.kareev.clientserver.Command;
import ru.kareev.clientserver.CommandType;
import ru.kareev.clientserver.commands.AuthOkCommandData;
import ru.kareev.clientserver.commands.ErrorCommandData;

import java.io.IOException;

public class AuthController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button authButton;

    private ReadCommandListener readCommandListener;

    @FXML
    public void executeAuth(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();

        if (login == null || password == null || password.isBlank() || login.isBlank()) {
            Dialogs.AuthError.EMPTY_CREDENTIALS.show();
            return;
        }

        if (!connectToServer()) {
            Dialogs.NetworkError.SERVER_CONNECT.show();
        }

        try {
            Network.getInstance().sendAuthMessage(login, password);
        } catch (IOException e) {
            Dialogs.NetworkError.SEND_MESSAGE.show();
            e.printStackTrace();
        }
    }

    private boolean connectToServer() {
        Network network = Network.getInstance();
        return network.isConnected() || network.connect();
    }

    public void initializeMessageHandler() {
        readCommandListener = getNetwork().addReadMessageListener(new ReadCommandListener() {
            @Override
            public void processReceivedCommand(Command command) {
                if (command.getType() == CommandType.AUTH_OK) {
                    AuthOkCommandData data = (AuthOkCommandData) command.getData();
                    String userName = data.getUsername();
                    Platform.runLater(() -> {
                        ClientChat.INSTANCE.switchToMainChatWindow(userName);
                    });
                } else if (command.getType() == CommandType.ERROR) {
                    ErrorCommandData data = (ErrorCommandData) command.getData();
                    String errorMessage = data.getErrorMessage();
                    Platform.runLater(() -> {
                        Dialogs.AuthError.INVALID_CREDENTIALS.show(errorMessage);
                    });
                }
            }
        });
    }

    public void close() {
        getNetwork().removeReadMessageListener(readCommandListener);
    }

    public Network getNetwork() {
        return Network.getInstance();
    }

}
