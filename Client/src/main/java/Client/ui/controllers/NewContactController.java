package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.components.ContactCard;
import Client.ui.components.ErrorMessageUi;
import Client.ui.models.Contact;
import Client.ui.models.CurrentUserAccount;
import exceptions.UserNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.FriendEntity;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewContactController implements Initializable {


    @FXML
    private TextField newContactPhoneField;
    @FXML
    private VBox errorContainer = new VBox();

    @FXML
    private VBox contactsToAddListView;


    List<String> contactsToAdd;
    ObservableList addedContacts;


    @FXML
    void addContact(MouseEvent event) {
        try {
            CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();
            RMIClientServices.sendFriendRequest(currentUserAccount.getPhoneNumber(), contactsToAdd);
            System.out.println("sending to" + contactsToAdd);
            errorContainer.getChildren().setAll(new ErrorMessageUi("Requestes sent succesfully" ,false));
            contactsToAddListView.getChildren().clear();

        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void addNumber(MouseEvent event) {
        try {
            //TODO Check phoneNumber in DB Here
            //If PhoneNumber exists
            CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();
            RMIClientServices.checkUserExists(newContactPhoneField.getText());
            errorContainer.getChildren().clear();
            contactsToAdd.add(newContactPhoneField.getText());
            FriendEntity newFriend = RMIClientServices.searchFriend(newContactPhoneField.getText());
            Contact newContact = new Contact(newFriend);
            contactsToAddListView.getChildren().add(new ContactCard(newContact));
            newContactPhoneField.clear();

        } catch (UserNotFoundException e) {
            //if phone number doesn't exist
            errorContainer.getChildren().setAll(new ErrorMessageUi("No such user!",true));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactsToAdd = new ArrayList<>();
        addedContacts = FXCollections.emptyObservableList();
    }
}
