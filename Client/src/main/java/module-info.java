module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires java.rmi;
    requires SharedUtilities;


    opens Server to javafx.fxml;
    exports Server;
    exports Server.controllers;
    exports Server.controllerutils;
    exports Server.network.interfaces;
    opens Server.controllers to javafx.fxml;
}