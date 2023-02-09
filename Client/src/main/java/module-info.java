module com.java.iti {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires java.rmi;
    requires SharedUtilities;
    requires Server;


    opens Client to javafx.fxml;
    exports Client;
    exports Client.ui.controllers;
    exports Client.ui.controllerutils;
//    exports Client.network.interfaces;
    opens Client.ui.controllers to javafx.fxml;
}