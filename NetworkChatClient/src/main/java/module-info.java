module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.kareev.client to javafx.fxml;
    exports ru.kareev.client;
    exports ru.kareev.client.controllers;
    opens ru.kareev.client.controllers to javafx.fxml;
}