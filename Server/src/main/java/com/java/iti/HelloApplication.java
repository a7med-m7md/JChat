package com.java.iti;


import com.java.iti.utils.RMIConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class HelloApplication extends Application {
    Connection connection;
    @Override
    public void start(Stage stage) throws IOException {
        RMIConnection.connect();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("JChat Server!");
//        connection = ConnectionDBManager.getInstance().getConnection();
//        try {
//            //TODO -> you can use connection of db here
//            System.out.println(connection.isClosed());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}