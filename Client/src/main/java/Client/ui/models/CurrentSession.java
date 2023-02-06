package Client.ui.models;

import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.image.Image;

public class CurrentSession {
    private static final CurrentSession currentSession = new CurrentSession();
    MyAccount myAccount;
    private ObservableList<Contact> contactsList = FXCollections.observableArrayList();
//    private ObservableList<Group> groupsList;
//    private ObservableMap<Contact, ObservableList<Message>> chatsMap = FXCollections.observableHashMap();
    private MapProperty<Contact, ObservableList<Message>> chatsMapProperty =
            new SimpleMapProperty<>(FXCollections.observableHashMap());

//    private ObservableMap<Group, ObservableList<Message>> groupChatsMap = FXCollections.observableHashMap();
    private ObservableList<Contact> onlineContactsList;
    private final ObjectProperty<Contact> currentContactChat = new SimpleObjectProperty<>();

    private CurrentSession() {}

    public static CurrentSession getInstance() {return currentSession;}

    public static CurrentSession getCurrentSession() {
        return currentSession;
    }

    public MyAccount getMyAccount() {
        return myAccount;
    }

    public ObservableList<Contact> getContactsList() {
        return contactsList;
    }

//    public ObservableMap<Contact, ObservableList<Message>> getChatsMap() {
//        return chatsMap;
//    }

    public ObservableList<Contact> getOnlineContactsList() {
        return onlineContactsList;
    }


    public void setMyAccount(MyAccount myAccount) {
        this.myAccount = myAccount;
    }

    public void setContactsList(ObservableList<Contact> contactsList) {
        this.contactsList = contactsList;
    }

//    public void setChatsMap(ObservableMap<Contact, ObservableList<Message>> chatsMap) {
//        this.chatsMap = chatsMap;
//    }

    public void setOnlineContactsList(ObservableList<Contact> onlineContactsList) {
        this.onlineContactsList = onlineContactsList;
    }
    public void addChat(Contact contact)
    {
        ObservableList messages = FXCollections.observableArrayList();
        messages.add(new Message(contact,"ass"));
        messages.add(new Message(contact,"ass"));
        messages.add(new Message(contact,"ass"));
        messages.add(new Message(contact,"ass"));
        messages.add(new Message(contact,"mou"));
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
