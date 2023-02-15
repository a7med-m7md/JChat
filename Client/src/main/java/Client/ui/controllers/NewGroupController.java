package Client.ui.controllers;

import Client.network.RMIClientServices;
import Client.ui.components.ContactCard;
import Client.ui.components.ErrorMessageUi;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import exceptions.UserNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import model.FriendEntity;
import model.Group;
import model.GroupMessageEntity;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewGroupController implements Initializable {

    @FXML
    private Circle groupPicture;

    @FXML
    private TextField groupName;

    @FXML
    private TextField groupDescription;

    @FXML
    private TextField newContactPhoneField;

    @FXML
    private VBox errorContainer;

    @FXML
    private VBox contactsToAddListView;


    List<String> contactsToAdd;

    Image groupImage;

    CurrentSession currentSession = CurrentSession.getInstance();

    List<FriendEntity> groupFriendList;


    @FXML
    void createGroup(MouseEvent event) {
        try {
            Group newGroup = new Group(groupName.getText(),groupDescription.getText(),CurrentUserAccount.getInstance().getMobile());
            //Adding Myself as a member
            groupFriendList.add(RMIClientServices.searchFriend(CurrentUserAccount.getInstance().getMobile()));
            newGroup.setListMembers(groupFriendList);
             newGroup = RMIClientServices.createGroup(newGroup);
            ObservableList<GroupMessageEntity> newGroupMessages = FXCollections.observableArrayList();
            currentSession.groupChatsMapProperty().put(newGroup, newGroupMessages);


        //closing the newGroupPane
        GroupsController.getInstance().rootPane.getChildren().remove(GroupsController.getInstance().newGroupPane);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addNumber(MouseEvent event) {
        errorContainer.getChildren().removeAll();
        try {
            //If PhoneNumber exists
            CurrentUserAccount currentUserAccount = CurrentUserAccount.getInstance();
            RMIClientServices.checkUserExists(newContactPhoneField.getText());
            errorContainer.getChildren().clear();
            contactsToAdd.add(newContactPhoneField.getText());
            FriendEntity newFriend = RMIClientServices.searchFriend(newContactPhoneField.getText());
            groupFriendList.add(newFriend);
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

    @FXML
    void openFileChooser(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            groupImage = new Image(file.toURI().toString());
            groupPicture.setFill(new ImagePattern(groupImage));
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupImage = new Image(getClass().getResourceAsStream("/images/group-image-placeholder.png"));
        contactsToAdd = FXCollections.observableArrayList();
        groupFriendList = new ArrayList<>();
    }
}
