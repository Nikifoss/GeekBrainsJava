module NetworkChatClient {
    requires javafx.controls;
    requires javafx.fxml;
    requires NetworkChatClientServer;

    exports ru.kareev.client;
    exports ru.kareev.client.controllers;
    exports ru.kareev.client.model;
    opens ru.kareev.client to javafx.fxml;
    opens ru.kareev.client.controllers to javafx.fxml;
    opens ru.kareev.client.model to javafx.fxml;

}
