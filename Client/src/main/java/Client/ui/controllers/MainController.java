package Client.ui.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @FXML
    private StackPane conversationArea;

    @FXML
    private StackPane tabContentArea;

    private ListView<?> conversationsList;

    @FXML
    private ComboBox<?> userStatus;

    @FXML
    void handleSendBtn(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Parent chats = FXMLLoader.load(getClass().getResource("/FXML/chats.fxml"));
            Parent conversations = FXMLLoader.load(getClass().getResource("/FXML/conversation.fxml"));
            tabContentArea.getChildren().add(chats);
            conversationArea.getChildren().add(conversations);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
