module com.java.iti.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.java.iti.client to javafx.fxml;
    exports com.java.iti.client;
}