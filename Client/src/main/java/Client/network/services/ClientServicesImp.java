package Client.network.services;

import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FriendEntity;
import model.MessageEntity;
import model.GroupMessageEntity;
import model.user.UserStatus;
import services.ClientServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientServicesImp extends UnicastRemoteObject implements ClientServices {
    public ClientServicesImp() throws RemoteException {
    }

    @Override
    public String getMobile() throws RemoteException {
        System.out.println("FROM User side");
        System.out.println(CurrentUserAccount.getMyAccount().getMobile());
        return CurrentUserAccount.getInstance().getMobile();
    }

    @Override
    public void friendRequestNotification(FriendEntity friend) throws RemoteException {
        System.out.println("Friend Request From : " + friend.getMobile());
        CurrentSession currentSession = CurrentSession.getInstance();
        currentSession.requestsListProperty().add(new Contact(friend));
        System.out.println("request from " + friend.getMobile());

    }

    @Override
    public void  receiveMessage(MessageEntity msg) throws RemoteException {

        CurrentSession currentSession = CurrentSession.getInstance();
        Contact senderContact = currentSession.getContactByPhone(msg.getSender());
        ObservableList<MessageEntity> firstTimeChat = FXCollections.observableArrayList();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if (currentSession.chatsMapProperty().keySet().stream().anyMatch(contact1 -> contact1.getMobile().equals(senderContact.getMobile()))) {
                    currentSession.chatsMapProperty().get(senderContact).add(msg);
                } else {
                    currentSession.chatsMapProperty().put(senderContact, firstTimeChat);
                    currentSession.chatsMapProperty().get(senderContact).add(msg);
                }
                System.out.println(currentSession.chatsMapProperty().get(senderContact));
                System.out.println("Msg send from: " + msg.getSender() + " Msg body: " + msg.getMSGBody());
                //Scrolls Down Automatically when new messages added
            }
        });
    }

    @Override
    public void receiveAnnouncement(String msg) throws RemoteException {
        System.out.println("Announcement from server: " + msg);
    }

    @Override
    public void receiveFriendStatus(String mobile, UserStatus status) throws RemoteException {
        CurrentSession currentSession = CurrentSession.getInstance();
        Contact changedStatusContact =
                currentSession.getContactsList()
                        .stream()
                        .filter(contact -> contact.getMobile().equals(mobile))
                        .findFirst()
                        .get();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                changedStatusContact.setStatus(status);
            }
        });
    }

    @Override
    public void receiveMessageFromGroup(GroupMessageEntity msg) throws RemoteException {
        System.out.println("Group:: " + msg.getGroupId() + "MSG :: " + msg.getMessage() + "Sent from:: " + msg.getSender());
    }


}
