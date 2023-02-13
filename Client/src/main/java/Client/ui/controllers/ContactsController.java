package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.components.ContactCard;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
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

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
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
                            loadFriends(currentUserAccount.getMobile())
                            .stream()
                            .map(Contact::new)
                            .collect(Collectors.toList()));
            currentSession.setContactsList(contactsList);

//          construction of contact card to display in the list
            contactsListView.itemsProperty().bind(Bindings.createObjectBinding(() -> FXCollections.observableArrayList(currentSession.contactsListProperty())));
            ChatsController chatsController = ChatsController.getInstance();
            contactsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

            //binding the selected item in the contactlist to a new chat on the chats list and open conversation
            contactsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("Selected item: " + contactsListView.getSelectionModel().getSelectedItem());
                Contact currentContact = contactsList.stream()
                        .filter((contact -> contact.getMobile().equals(contactsListView.getSelectionModel().getSelectedItem().getMobile()))).findFirst().get();
                currentSession.addChat(currentContact);
                chatsController.conversationsList.getSelectionModel().select(currentContact);
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
