module com.java.iti.server {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires java.naming;
    requires java.rmi;

    opens com.java.iti to javafx.fxml;
    exports com.java.iti;
    exports com.java.iti.utils.interfaces;
}