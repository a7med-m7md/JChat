package Client;


//import Client.network.RMIConnection;
import Client.network.RMIConnection;
import Models.UtilityClass;
import exceptions.UserNotFoundException;
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
//        RMIConnection.connect();
        try {
            RMIConnection.logIn("01112175312", "152365412");
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        UtilityClass.isNull();
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
