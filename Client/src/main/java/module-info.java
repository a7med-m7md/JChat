module Client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires java.rmi;
    requires SharedUtilities;
    requires java.desktop;
    requires javafx.swing;


    opens Client to javafx.fxml;
    exports Client;
    exports Client.ui.controllers;
    exports Client.ui.controllerutils;
//    exports Client.network.interfaces;
    opens Client.ui.controllers to javafx.fxml;
}