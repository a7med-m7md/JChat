package Client.ui.controllers;


import Client.network.RMIClientServices;
import Client.ui.components.ConversationCard;
import Client.ui.components.FriendRequestCard;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RequestsController implements Initializable {

    @FXML
    private ListView<Contact> requestsListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            CurrentSession currentSession = CurrentSession.getInstance();
            CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();

            ObservableList<Contact> requestsList =
                    FXCollections.observableArrayList(RMIClientServices.loadFriendsRequest(currentUserAccount.getPhoneNumber())
                            .stream()
                            .map(Contact::new)
                            .collect(Collectors.toList()));
            currentSession.setRequestsList(requestsList);

            RMIClientServices.loadFriendsRequest(currentUserAccount.getPhoneNumber());
            requestsListView.itemsProperty().bind(Bindings.createObjectBinding(() -> FXCollections.observableArrayList(currentSession.requestsListProperty())));
            requestsListView.setCellFactory(listView -> new ListCell<Contact>() {
                @Override
                protected void updateItem(Contact contact, boolean empty) {
                    super.updateItem(contact, empty);
                    if (empty || contact == null) {
                        setGraphic(null);
                    } else {
                        FriendRequestCard requestCard = new FriendRequestCard(contact);
                        setGraphic(requestCard);
                    }
                }
            });

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
