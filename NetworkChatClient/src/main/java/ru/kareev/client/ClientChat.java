package ru.kareev.client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ru.kareev.client.controllers.AuthController;
import ru.kareev.client.controllers.ClientController;

import java.io.IOException;

public class ClientChat extends Application {

    public static final String CONNECTION_ERROR_MESSAGE = "Невозможно установить сетевое соединение";

    private Stage primaryStage;
    private Stage authStage;

    @Override
    public void start(Stage stage) throws IOException {
        this.primaryStage = stage;

        ClientController controller = CreateChatDialog(stage);
        createAuthDialog();

        controller.initializeMessageHandler();
    }

    private void createAuthDialog() throws IOException {
        FXMLLoader authLoader = new FXMLLoader();
        authLoader.setLocation(getClass().getResource("auth-dialog.fxml"));
        AnchorPane authDialogPanel = authLoader.load();

        authStage = new Stage();
        authStage.initOwner(primaryStage);
        authStage.initModality(Modality.WINDOW_MODAL);

        authStage.setScene(new Scene(authDialogPanel));
        AuthController authController = authLoader.getController();
        authController.setClientChat(this);
        authController.initializeMessageHandler();
        authStage.showAndWait();
    }

    private ClientController CreateChatDialog(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("chat-template.fxml"));

        Parent load = fxmlLoader.load();
        Scene scene = new Scene(load);

        this.primaryStage.setTitle("Онлайн чат GeekBrains");
        this.primaryStage.setScene(scene);

        ClientController controller = fxmlLoader.getController();
        controller.userList.getItems().addAll("username1", "username2", "username3");

        stage.show();

        connectToServer(controller);
        return controller;
    }

    private void connectToServer(ClientController clientController) {
        boolean result = Network.getInstance().connect();

        if(!result){
            System.err.println(CONNECTION_ERROR_MESSAGE);
            showErrorDialog(CONNECTION_ERROR_MESSAGE);
        }

        clientController.setApplication(this);

        this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Network.getInstance().close();
            }
        });
    }


    public void showErrorDialog(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public Stage getAuthStage() {
        return authStage;
    }

    public static void main(String[] args) {
        launch();
    }

    public Stage getChatStage() {
        return this.primaryStage;
    }
}