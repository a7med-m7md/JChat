package Client;


import Client.network.RMIClientServices;
import Client.ui.models.CurrentUserAccount;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.user.UserStatus;

import java.io.IOException;

public class Main extends Application {
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
    public void init() throws Exception {
        super.init();
        //TODO -> logic for transferring file here in client side
        /*Scanner scanner = new Scanner(System.in);
        int userId = scanner.nextInt();
        //senderUserId -> 10, receiverUserId -> 11
        if (userId == 10){
            Platform.runLater(() -> {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(null);
                if (file != null){
                    System.out.println("choosed path file ->"+file.getAbsolutePath());
                    FileService fileService = new FileService(file,userId);
                    fileService.startConnection();
                    fileService.sendFile(file);
                }
            });
        }else{
            FileService fileService = new FileService(userId);
            fileService.startConnection();
        }*/

    }

    @Override
    public void stop() throws Exception {
        RMIClientServices.tellMyStatus(CurrentUserAccount.getMyAccount().getMobile(), UserStatus.OFFLINE);
//        RMIClientServices.disconnect();
        System.out.println(CurrentUserAccount.getMyAccount().getMobile() + " Closed");
    }
}
