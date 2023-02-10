package Client.ui.models;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;
import model.user.UserEntity;

public class CurrentSession {
    private static final CurrentSession currentSession = new CurrentSession();
    SimpleObjectProperty<CurrentUserAccount> myAccount;
    private ObservableList<Contact> contactsList = FXCollections.observableArrayList();
    private MapProperty<Contact, ObservableList<Message>> chatsMapProperty =
            new SimpleMapProperty<>(FXCollections.observableHashMap());

    private ObservableList<Contact> onlineContactsList;
    private final ObjectProperty<Contact> currentContactChat = new SimpleObjectProperty<>();

    private CurrentSession() {
    }

    public static CurrentSession getInstance() {
        return currentSession;
    }

    public static CurrentSession getCurrentSession() {
        return currentSession;
    }


    public ObservableList<Contact> getContactsList() {
        return contactsList;
    }


    public ObservableList<Contact> getOnlineContactsList() {
        return onlineContactsList;
    }



    public void setContactsList(ObservableList<Contact> contactsList) {
        this.contactsList = contactsList;
    }

    public void setOnlineContactsList(ObservableList<Contact> onlineContactsList) {
        this.onlineContactsList = onlineContactsList;
    }

    public void addChat(Contact contact) {
        ObservableList messages = FXCollections.observableArrayList();
        messages.add(new Message(contact, "ass"));
        messages.add(new Message(contact, "ass"));
        messages.add(new Message(contact, "ass"));
        messages.add(new Message(contact, "ass"));
        messages.add(new Message(contact, "mou"));
        contactsList.add(contact);
        chatsMapProperty.put(contact, messages);

    }

    public ObjectProperty<Contact> currentContactChatProperty() {
        return currentContactChat;
    }

    public MapProperty<Contact, ObservableList<Message>> chatsMapProperty() {
        return chatsMapProperty;
    }

}
