module com.java.iti.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;
    requires java.rmi;


    opens com.java.iti to javafx.fxml;
    exports com.java.iti;
    exports com.java.iti.controllers;
    exports com.java.iti.utils.interfaces;
    opens com.java.iti.controllers to javafx.fxml;
}