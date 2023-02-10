package Client.ui.controllers;

import Client.ui.components.ContactCard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ContactsController implements Initializable {

    @FXML
    private VBox addContactContainer;
    @FXML
    private ListView<ContactCard> contactList;

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

    }
}
