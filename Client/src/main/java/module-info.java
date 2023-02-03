module com.java.iti.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires java.rmi;


    opens com.java.iti.client to javafx.fxml;
    exports com.java.iti.client;
    exports com.java.iti.client.controllers;
    exports com.java.iti.utils.interfaces;
    opens com.java.iti.client.controllers to javafx.fxml;
}