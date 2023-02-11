package Client.ui.controllers;

import Client.ui.components.ErrorMessageUi;
import Client.ui.components.NotificationUI;
import Client.ui.models.CurrentUserAccount;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ToggleButton chatsToggle;

    @FXML
    private ToggleGroup tabs;

    @FXML
    private ToggleButton groupsToggle;

    @FXML
    private ToggleButton contactsToggle;

    @FXML
    private ToggleButton requestsToggle;

    @FXML
    private ToggleButton accountToggle;

    @FXML
    private StackPane tabContentArea;

    @FXML
    private StackPane currentUserPane;

    @FXML
    private StackPane conversationArea;

    @FXML
    private StackPane sideBar;


    @FXML
    private VBox requestsNotification;


    Map<String, Parent> tabPanes = FXCollections.observableHashMap();


    @FXML
    void logOut(MouseEvent event) {

    }

    @FXML
    void switchAccountSettings(MouseEvent event) {
        if (!tabPanes.containsKey("account")) {
            try {
                Parent accountPane = FXMLLoader.load(getClass().getResource("/FXML/account-info.fxml"));
                tabPanes.put("account", accountPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Parent accountPane = tabPanes.get("account");
        animateTabs(accountPane);
    }

    @FXML
    void switchChats(MouseEvent event) {
        if (!tabPanes.containsKey("chats")) {
            try {
                Parent chatsPane = FXMLLoader.load(getClass().getResource("/FXML/chats.fxml"));
                tabPanes.put("chats", chatsPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Parent chatsPane = tabPanes.get("chats");
        animateTabs(chatsPane);

    }

    @FXML
    void switchContactsList(MouseEvent event) {
//        if (!tabPanes.containsKey("contacts")) {
            try {
                Parent contactsPane = FXMLLoader.load(getClass().getResource("/FXML/contacts.fxml"));
//                tabPanes.put("contacts", contactsPane);
        animateTabs(contactsPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//        }
//        Parent contactsPane = tabPanes.get("contacts");

    }


    @FXML
    void switchRequests(MouseEvent event) {

    }

    @FXML
    void switchGroups(MouseEvent event) {
        if (!tabPanes.containsKey("groups")) {
            try {
                Parent groupsPane = FXMLLoader.load(getClass().getResource("/FXML/groups.fxml"));
                tabPanes.put("groups", groupsPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Parent groupsPane = tabPanes.get("groups");
        animateTabs(groupsPane);
    }

    private void animateTabs(Parent tabPane) {
        tabPane.translateXProperty().set(-tabContentArea.getWidth());
        tabContentArea.getChildren().setAll(tabPane);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(tabPane.translateXProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(.2), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {

        });
        timeline.play();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sideBar.toFront();
        // opening chats tab on startup
        try {
            //TODO Add Notification for friend reuests

            requestsNotification.getChildren().add(new NotificationUI("2",false));

            //TODO
            Parent chatsPane = FXMLLoader.load(getClass().getResource("/FXML/chats.fxml"));
            Parent conversations = FXMLLoader.load(getClass().getResource("/FXML/conversation.fxml"));
            Parent currentUser = FXMLLoader.load(getClass().getResource("/FXML/current-user-card.fxml"));
            tabContentArea.getChildren().add(chatsPane);
            conversationArea.getChildren().add(conversations);
            currentUserPane.getChildren().add(currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
