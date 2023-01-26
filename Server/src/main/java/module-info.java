module com.java.iti.server {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.java.iti.server to javafx.fxml;
    exports com.java.iti.server;
}