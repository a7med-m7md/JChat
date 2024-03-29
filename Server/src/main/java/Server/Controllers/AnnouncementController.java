package Server.Controllers;

import Server.business.services.serversservices.Services;
import Server.network.services.ChatServiceImp;
import Server.persistance.dao.UserDao;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.user.UserEntity;
import services.ChatService;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AnnouncementController implements Initializable {

    @FXML
    private ImageView back;
    @FXML
    private Button sendBtn;
    @FXML
    private TextArea msg;
    public AnnouncementController() {

    }

  @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

      back.setOnMouseClicked((ev) -> {
            Scene home = null;
            try {
                home = new Scene(FXMLLoader.load(getClass().getResource("/FXML/ServerServices.fxml")));
                Node node = (Node) ev.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                Stage homeStage = new Stage();
                homeStage.setScene(home);
                homeStage.setResizable(true);
                homeStage.show();
                stage.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

      sendBtn.setOnAction((event -> {
          try {
              ChatService chatService = new ChatServiceImp();
              chatService.sendAnnouncementToUser(msg.getText());
              msg.setText("");
          } catch (RemoteException e) {
              e.printStackTrace();
          }

      }));

    }

}
