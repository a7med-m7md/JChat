package Client.ui.controllers;

import Client.ui.components.ConversationCard;
import Client.ui.models.Contact;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatsController implements Initializable {

    @FXML
    private StackPane onlineContactsPane;
    @FXML
    private ImageView emptyPlaceholder;


    private static ChatsController instance = null;

    public static ChatsController getInstance() {
        if (instance == null) {
            instance = new ChatsController();
        }
        return instance;
    }

    private ChatsController() {
    }


    @FXML
    public ListView<Contact> conversationsList;

    CurrentSession currentSession = CurrentSession.getInstance();

    @FXML
    void newChat(MouseEvent event) {
        MainController mainController = MainController.getInstance();
        mainController.switchTab("/FXML/contacts.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        conversationsList.itemsProperty().bind(Bindings.createObjectBinding(() -> FXCollections.observableArrayList(CurrentSession.getInstance().chatsMapProperty().keySet()), CurrentSession.getInstance().chatsMapProperty()));
        currentSession.currentContactChatProperty().bind(conversationsList.getSelectionModel().selectedItemProperty());
        //load conversation Pane when an item is selected
        conversationsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Contact>() {
            @Override
            public void changed(ObservableValue<? extends Contact> observable, Contact oldValue, Contact newValue) {
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


        currentSession.currentContactChatProperty().addListener((observable, oldValue, newValue) -> {
            conversationsList.getSelectionModel().select(newValue);
        });

        conversationsList.setCellFactory(listView -> new ListCell<Contact>() {
            @Override
            protected void updateItem(Contact contact, boolean empty) {
                super.updateItem(contact, empty);
                if (empty || contact == null) {
                    setGraphic(null);
                } else {
                    ConversationCard conversationCard = new ConversationCard(contact, currentSession.chatsMapProperty().get(contact));
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

        emptyPlaceholder.visibleProperty().bind(currentSession.chatsMapProperty().emptyProperty());


    }


}
