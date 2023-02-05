module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires java.rmi;
    requires SharedUtilities;


    opens Client to javafx.fxml;
    exports Client;
    exports Client.controllers;
    exports Client.controllerutils;
//    exports Client.network.interfaces;
    opens Client.controllers to javafx.fxml;
}