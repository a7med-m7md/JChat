package Client.ui.controllers;

import Client.ui.components.GroupConversationCard;
import Client.ui.models.CurrentSession;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Group;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GroupsController implements Initializable {

    private static GroupsController instance = null;

    public static GroupsController getInstance() {
        if (instance == null) {
            instance = new GroupsController();
        }
        return instance;
    }

    private GroupsController() {
    }

    @FXML
    private StackPane serverAnnouncementsPane;

    @FXML
    private StackPane contentPane;

    @FXML
    private ListView<Group> conversationsList;

    @FXML
    private ImageView emptyPlaceholder;

    @FXML
    StackPane rootPane;

    CurrentSession currentSession = CurrentSession.getInstance();
    Parent newGroupPane;
    @FXML
    void newGroup(MouseEvent event) {
        try {
            newGroupPane = FXMLLoader.load(getClass().getResource("/FXML/new-group.fxml"));
            rootPane.getChildren().add(newGroupPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conversationsList.itemsProperty().bind(Bindings.createObjectBinding(() -> FXCollections.observableArrayList(CurrentSession.getInstance().groupChatsMapProperty().keySet()), CurrentSession.getInstance().getGroupChatsMap()));

        currentSession.currentGroupChatProperty().bind(conversationsList.getSelectionModel().selectedItemProperty());

        conversationsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Group>() {
            @Override
            public void changed(ObservableValue<? extends Group> observable, Group oldValue, Group newValue) {
                // Your action here
                Parent conversationPane = null;
                try {
                    conversationPane = FXMLLoader.load(getClass().getResource("/FXML/conversation.fxml"));
                    MainController mainController = MainController.getInstance();
                    mainController.conversationArea.getChildren().removeAll();
                    mainController.conversationArea.getChildren().setAll(conversationPane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        currentSession.currentGroupChatProperty().addListener((observable, oldValue, newValue) -> {
            conversationsList.getSelectionModel().select(newValue);
        });

        conversationsList.setCellFactory(listView -> new ListCell<Group>() {
            @Override
            protected void updateItem(Group group, boolean empty) {
                super.updateItem(group, empty);
                if (empty || group == null) {
                    setGraphic(null);
                } else {
                    GroupConversationCard groupConversationCard = new GroupConversationCard(group, currentSession.groupChatsMapProperty().get(group));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setGraphic(groupConversationCard);
                        }
                    });
                }
            }
        });






    }
}
