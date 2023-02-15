package Client;


import Client.network.RMIClientServices;
import Client.network.services.ClientServicesImp;
import Client.network.services.filermi.FileClientImpl;
import Client.network.services.filesocket.FileService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ClientInt;
import services.FileClientInt;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main extends Application {
    FileClientInt clientInt;
    int userId;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/FXML/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("JChat");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("start close the client from stop method");
        //TODO -> when close the window or logout client
        RMIClientServices.unRegisterClientOnFileServer(clientInt);
        //TODO -> send client id here
        FileService.getInstance().stopClient(userId);
    }

    @Override
    public void init() throws Exception {
        super.init();
        clientInt = new FileClientImpl();
        RMIClientServices.registerClientOnFileServer(clientInt);
        //FileService.getInstance().startConnection(userId);
        //TODO -> set user Id here;
        Scanner scanner = new Scanner(System.in);
        userId = scanner.nextInt();
        //senderUserId -> 10, receiverUserId -> 11
        if (userId == 10) {
            FileService.getInstance().startConnection(userId);
            Platform.runLater(() -> {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(null);
                if (file != null) {
                    System.out.println("choosed path file ->" + file.getAbsolutePath());
                    List<Long> groupList = new ArrayList<>();
                    groupList.add(Long.valueOf(11));
                    groupList.add(Long.valueOf(12));
                    FileService.getInstance().sendFileToGroup(file,10 , groupList);
                }
            });
        } else {
            FileService.getInstance().startConnection(userId);
        }

    }
}
