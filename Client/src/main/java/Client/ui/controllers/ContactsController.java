package Client.ui.controllers;

import Client.ui.components.ContactCard;
import Client.ui.components.FriendRequestCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.FriendEntity;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactsController implements Initializable {

    @FXML
    private VBox addContactContainer;
    @FXML
    private VBox contactList;

    @FXML
    void addContact(MouseEvent event) {
        try {
            Parent newContactPane = FXMLLoader.load(getClass().getResource("/FXML/new-contact-pane.fxml"));
            addContactContainer.getChildren().setAll(newContactPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FriendEntity testFriend = new FriendEntity();
        testFriend.setName("Test");
        testFriend.setBio("Test");
        contactList.getChildren().add(new FriendRequestCard(testFriend));
        contactList.getChildren().add(new FriendRequestCard(testFriend));
        contactList.getChildren().add(new FriendRequestCard(testFriend));
        contactList.getChildren().add(new FriendRequestCard(testFriend));
    }
}
