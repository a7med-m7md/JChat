module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires java.naming;
    requires java.rmi;
    requires SharedUtilities;
    requires Ab;
//    requires Client;

    opens Server to javafx.fxml;
    exports Server;
    exports Server.persistance.entities;
    exports Server.business.model.user;
//    exports Server.network.interfaces;
}