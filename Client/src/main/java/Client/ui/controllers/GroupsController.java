package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.components.ConversationCard;
import Client.ui.components.GroupConversationCard;
import Client.ui.components.StyledChatMessage;
import Client.ui.controllerutils.ChatType;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.GroupMessageEntity;
import model.MessageEntity;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class GroupsController implements Initializable {

    private static GroupsController instance = null;
    private Group currentGroupChat;

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
        currentSession = CurrentSession.getInstance();
        try {
            RMIClientServices.getAllMyGroups(CurrentUserAccount.getInstance().getMobile()).stream().forEach((group -> currentSession.groupChatsMapProperty().put(group,FXCollections.observableArrayList())));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        conversationsList.itemsProperty().bind(Bindings.createObjectBinding(() -> FXCollections.observableArrayList(CurrentSession.getInstance().groupChatsMapProperty().keySet()), CurrentSession.getInstance().groupChatsMapProperty()));
        currentSession.currentGroupChatProperty().bind(conversationsList.getSelectionModel().selectedItemProperty());

        //load conversation Pane when an item is selected
        conversationsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Group>() {
            @Override
            public void changed(ObservableValue<? extends Group> observable, Group oldValue, Group newValue) {
                // Your action here
                Parent conversationPane = null;
                try {
                    conversationPane = FXMLLoader.load(getClass().getResource("/FXML/group-conversation.fxml"));
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
                    GroupConversationCard conversationCard = new GroupConversationCard(group, currentSession.groupChatsMapProperty().get(group));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setGraphic(conversationCard);
                            //Scrolls Down Automatically when new messages added
                        }
                    });
                }
            }
        });

        emptyPlaceholder.visibleProperty().bind(currentSession.groupChatsMapProperty().emptyProperty());





    }
}
