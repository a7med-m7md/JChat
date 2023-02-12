module Server {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires java.naming;
    requires java.rmi;
    requires SharedUtilities;
//    requires Client;

    opens Server.Controllers to javafx.fxml;
    exports Server;
    opens Server to
            javafx.fxml;
//    exports Server.network.interfaces;
}