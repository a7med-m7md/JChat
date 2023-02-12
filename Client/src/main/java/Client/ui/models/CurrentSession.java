package Client.ui.models;

import Client.ui.controllers.MainController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import model.user.UserEntity;

public class CurrentSession {
    private static final CurrentSession currentSession = new CurrentSession();
    SimpleObjectProperty<CurrentUserAccount> myAccount;
    //    private ObservableList<Contact> contactsList = FXCollections.observableArrayList();
    private ListProperty<Contact> contactsList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ListProperty<Contact> requestsList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private MapProperty<Contact, ObservableList<Message>> chatsMapProperty =
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
        ObservableList messages = FXCollections.observableArrayList();
        if (!chatsMapProperty.containsKey(contact))
            chatsMapProperty.put(contact, messages);
        else currentContactChat.set(contact);
        MainController mainController = MainController.getInstance();
        mainController.switchTab("chats", "/FXML/chats.fxml");
    }

    public ObjectProperty<Contact> currentContactChatProperty() {
        return currentContactChat;
    }

    public MapProperty<Contact, ObservableList<Message>> chatsMapProperty() {
        return chatsMapProperty;
    }

}
