module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires java.naming;
    requires java.rmi;
    requires SharedUtilities;
//    requires Client;

    opens Server to javafx.fxml;
    opens Server.Controllers to javafx.fxml;
    exports Server;
    exports Server.Controllers;

//    exports Server.network.interfaces;
}