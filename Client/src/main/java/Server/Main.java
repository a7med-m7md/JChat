package Server;


import Server.network.RMIConnection;
import Models.UtilityClass;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/FXML/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        RMIConnection.connect();
        //UtilityClass.isNull();
        UtilityClass u = new UtilityClass();
        System.out.println(u.isNull());
        System.out.println();
        stage.setTitle("JChat");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }
}
