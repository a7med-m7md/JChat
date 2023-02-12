package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.components.ContactCard;
import Client.ui.components.ConversationCard;
import Client.ui.components.FriendRequestCard;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.FriendEntity;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ContactsController implements Initializable {

    @FXML
    private VBox addContactContainer;
    @FXML
    private ListView<Contact> contactsListView;


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
        try {
            CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();
            CurrentSession currentSession = CurrentSession.getInstance();

            //Retrieving User's contacts' list by RMI into an ObservableList
            ObservableList<Contact> contactsList =
                    FXCollections.observableArrayList(RMIClientServices.
                            loadFriends(currentUserAccount.getPhoneNumber())
                            .stream()
                            .map(Contact::new)
                            .collect(Collectors.toList()));
            currentSession.setContactsList(contactsList);

//          construction of contact card to display in the list
            contactsListView.itemsProperty().bind(Bindings.createObjectBinding(() -> FXCollections.observableArrayList(currentSession.contactsListProperty())));
            currentSession.currentContactChatProperty().bind(contactsListView.getSelectionModel().selectedItemProperty());

            contactsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            contactsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Selected item: " + newValue.getName());
                currentSession.addChat(newValue);
            });

            contactsListView.setCellFactory(listView -> new ListCell<Contact>() {
                @Override
                protected void updateItem(Contact contact, boolean empty) {
                    super.updateItem(contact, empty);

                    if (empty || contact == null) {
                        setGraphic(null);
                    } else {
                        ContactCard contactCard = new ContactCard(contact);
                        setGraphic(contactCard);
                    }
                }
            });

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
