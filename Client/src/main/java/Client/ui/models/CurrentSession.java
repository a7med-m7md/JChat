package Client.ui.models;

import Client.ui.controllers.ChatsController;
import Client.ui.controllers.MainController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import model.MessageEntity;
import model.user.UserEntity;

import java.util.NoSuchElementException;
import java.util.Optional;

public class CurrentSession {
    private static final CurrentSession currentSession = new CurrentSession();
    //    private ObservableList<Contact> contactsList = FXCollections.observableArrayList();
    private ListProperty<Contact> contactsList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ListProperty<Contact> requestsList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private MapProperty<Contact, ObservableList<MessageEntity>> chatsMapProperty =
            new SimpleMapProperty<>(FXCollections.observableHashMap());

    private ObservableList<Contact> onlineContactsList;
    private final ObjectProperty<Contact> currentContactChat = new SimpleObjectProperty<>();

    private CurrentSession() {
    }

    public static CurrentSession getInstance() {
        return currentSession;
    }


    public ObservableList<Contact> getRequestsList() {
        return requestsList.get();
    }

    public ListProperty<Contact> requestsListProperty() {
        return requestsList;
    }
    public Contact getMyAccount(CurrentUserAccount currentUserAccount) {
        Contact myAccount = new Contact();
        myAccount.setName(currentUserAccount.getName());
        myAccount.setBio(currentUserAccount.getBio());
        myAccount.setStatus(currentUserAccount.getStatus());
        myAccount.setPicture(currentUserAccount.getImage());
        return myAccount;
    }

    public void setRequestsList(ObservableList<Contact> requestsList) {
        this.requestsList.set(requestsList);
    }

    public ObservableList<Contact> getContactsList() {
        return contactsList.get();
    }

    public ListProperty<Contact> contactsListProperty() {
        return contactsList;
    }

    public void setContactsList(ObservableList<Contact> contactsList) {
        this.contactsList.set(contactsList);
    }

    public ObservableList<Contact> getOnlineContactsList() {
        return onlineContactsList;
    }


    public void setOnlineContactsList(ObservableList<Contact> onlineContactsList) {
        this.onlineContactsList = onlineContactsList;
    }

    public void addChat(Contact contact) {
        if (chatsMapProperty.keySet().stream().anyMatch(contact1 -> contact1.getMobile().equals(contact.getMobile())))
            ChatsController.getInstance().conversationsList.getSelectionModel().select(contact);
            else {
            ObservableList messages = FXCollections.observableArrayList();
            chatsMapProperty.put(contact, messages);
        }
        MainController mainController = MainController.getInstance();
        mainController.switchTab("chats", "/FXML/chats.fxml");
    }

    public ObjectProperty<Contact> currentContactChatProperty() {
        return currentContactChat;
    }

    public MapProperty<Contact, ObservableList<MessageEntity>> chatsMapProperty() {
        return chatsMapProperty;
    }

    public Contact getContactByPhone(String phoneNumber) {

        Optional<Contact> foundContact = contactsList.stream().filter((contact)->contact.getMobile().equals(phoneNumber)).findFirst();
        try {
        return foundContact.get();
        } catch (NoSuchElementException e) {
            return getMyAccount(CurrentUserAccount.getInstance());
        }
    }

}
