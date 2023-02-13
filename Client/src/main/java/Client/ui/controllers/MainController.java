package Client.ui.controllers;

import Client.ui.components.ErrorMessageUi;
import Client.ui.components.NotificationUI;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.NumberStringConverter;
import model.FriendEntity;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private static MainController instance = null;

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    private MainController() {
    }


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
    public StackPane conversationArea;

    @FXML
    private StackPane sideBar;


    @FXML
    private Label requestsNotification;


    Map<String, Parent> tabPanes = FXCollections.observableHashMap();

    IntegerProperty requestCount = new SimpleIntegerProperty();
    StringProperty notifcationLabel = new SimpleStringProperty();

    @FXML
    void logOut(MouseEvent event) {

    }

    @FXML
    void switchAccountSettings(MouseEvent event) {
        switchTab("account", "/FXML/account-info.fxml");
    }

    @FXML
    void switchChats(MouseEvent event) {
        switchTab("chats", "/FXML/chats.fxml");

    }

    @FXML
    void switchContactsList(MouseEvent event) {
        switchTab("/FXML/contacts.fxml");
    }


    @FXML
    void switchRequests(MouseEvent event) {
        switchTab("/FXML/requests.fxml");
    }

    @FXML
    void switchGroups(MouseEvent event) {
        switchTab("groups", "/FXML/groups.fxml");
    }

    public void switchTab(String groups, String name) {
        if (!tabPanes.containsKey(groups)) {
            try {
                Parent groupsPane = FXMLLoader.load(getClass().getResource(name));
                tabPanes.put(groups, groupsPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Parent tabPane = tabPanes.get(groups);
        animateTabs(tabPane);
    }

    public void switchTab(String name) {
        try {
            Parent groupsPane = FXMLLoader.load(getClass().getResource(name));
            animateTabs(groupsPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //Scrolls Down Automatically when new messages added/
                    // /TODO Add Notification for friend reuests
                    CurrentSession currentSession = CurrentSession.getInstance();
                    requestCount.bind(Bindings.size(currentSession.requestsListProperty()));
                    System.out.println(requestCount.get());

                    BooleanBinding newNotification = requestCount.greaterThan(0);
                    requestsNotification.visibleProperty().bind(newNotification);
                    requestsNotification.textProperty().bind(requestCount.asString());
                }
            });

//            requestsNotification.textProperty().bind(Bindings.convert(currentSession.requestsListProperty().sizeProperty()));
//            notifcationLabel.bind(Bindings.convert(requestCount));


            //TODO

            ChatsController chatsController  = ChatsController.getInstance();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/chats.fxml"));
            loader.setController(chatsController);
            Parent chatsPane = loader.load();

//            Parent chatsPane = FXMLLoader.load(getClass().getResource("/FXML/chats.fxml"));
//            Parent conversations = FXMLLoader.load(getClass().getResource("/FXML/conversation.fxml"));
            Parent currentUser = FXMLLoader.load(getClass().getResource("/FXML/current-user-card.fxml"));
            tabPanes.put("chats", chatsPane);

            Parent contactsPane = FXMLLoader.load(getClass().getResource("/FXML/contacts.fxml"));
            tabContentArea.getChildren().add(contactsPane);
//            conversationArea.getChildren().add(conversations);
            currentUserPane.getChildren().add(currentUser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
