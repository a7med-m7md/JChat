module com.java.iti.server {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires java.naming;

    opens com.java.iti.server to javafx.fxml;
    exports com.java.iti.server;
}