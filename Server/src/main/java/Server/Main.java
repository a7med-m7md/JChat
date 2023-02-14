package Server;


import Server.network.services.RMIServerServices;
import Server.network.services.fileservice.SocketConnection;
import Server.persistance.ConnectionManager;
import javafx.stage.FileChooser;
import model.UtilityClass;
import Server.network.RMIConnectionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
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

        //SocketConnection socketConnection = new SocketConnection();
    }

    @Override
    public void stop() throws Exception {
        RMIConnectionManager.getInstance().disconnect();
        ConnectionManager.getInstance().close();
        //TODO -> close socket connection
    }
}