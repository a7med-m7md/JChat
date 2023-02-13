package Client.ui.controllers;

import Client.ui.components.ConversationCard;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatsController implements Initializable {

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
                    setGraphic(conversationCard);
                }
            }
        });

    }
}
