package Server;


import Server.business.services.filesocket.SocketConnection;
import Server.persistance.ConnectionManager;
import model.UtilityClass;
import Server.network.RMIConnectionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {
    Connection connection;
    RMIConnectionManager rmiConnection;

    @Override
    public void start(Stage stage) throws IOException {
        SocketConnection socketConnection = SocketConnection.getInstance();
        socketConnection.startConnection();
        System.out.println(UtilityClass.isNull());
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/ServerServices.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("JChat Server!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
//        rmiConnection = new RMIConnectionManager();
//        rmiConnection.startServices();
//        rmiConnection.connected();
//        ConnectionManager.getInstance().getConnection();

        //SocketConnection.getInstance().startConnection();
    }

    @Override
    public void stop() throws Exception {
        rmiConnection.disconnect();
        ConnectionManager.getInstance().close();
        //TODO -> handle close server here
        //SocketConnection.getInstance().disconnect();
    }
}