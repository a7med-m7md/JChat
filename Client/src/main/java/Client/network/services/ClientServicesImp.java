package Client.network.services;


import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.media.AudioClip;
import model.*;
import model.user.UserStatus;
import services.ClientServices;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

public class ClientServicesImp extends UnicastRemoteObject implements ClientServices {
    AudioClip clip;

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
    public void receiveMessage(MessageEntity msg) throws RemoteException {

        clip = new AudioClip("notification.wav");
        clip.play();
        CurrentSession currentSession = CurrentSession.getInstance();
        Contact senderContact = currentSession.getContactByPhone(msg.getSender());
        ObservableList<MessageEntity> firstTimeChat = FXCollections.observableArrayList();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String audioFilePath = getClass().getResource("/notification.wav").toExternalForm();
                clip = new javafx.scene.media.AudioClip(audioFilePath);
                clip.play();
                try {
                    displayTray();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                if (currentSession.chatsMapProperty().keySet().stream().anyMatch(contact1 -> contact1.getMobile().equals(senderContact.getMobile()))) {
                    currentSession.chatsMapProperty().get(senderContact).add(msg);
                } else {
                    currentSession.chatsMapProperty().put(senderContact, firstTimeChat);
                    currentSession.chatsMapProperty().get(senderContact).add(msg);
                }
                System.out.println(currentSession.chatsMapProperty().get(senderContact));
                System.out.println("Msg send from: " + msg.getSender() + " Msg body: " + msg.getMessage());
                //Scrolls Down Automatically when new messages added
            }
        });
    }

    @Override
    public void receiveAnnouncement(String msg) throws RemoteException {
        System.out.println("Announcement from server: " + msg);
        Platform.runLater(()->{
            JOptionPane.showMessageDialog(null, msg, "Server Announcement" , JOptionPane.INFORMATION_MESSAGE);
        });
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
        CurrentSession currentSession = CurrentSession.getInstance();
                Group messageGroup = msg.getGroup();
                Optional<Group> myGroup = currentSession.groupChatsMapProperty().keySet().stream()
                        .filter(group -> group.getId() == messageGroup.getId())
                        .findAny();
                currentSession.groupChatsMapProperty().get(myGroup.get()).add(msg);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            }
        });

    }

    @Override
    public void receiveGroupAddNotification(Group group) throws RemoteException {
        ObservableList<GroupMessageEntity> newGroupMessageList = FXCollections.observableArrayList();
        CurrentSession.getInstance().groupChatsMapProperty().put(group, newGroupMessageList);
    }


    public void displayTray() throws AWTException {
        //Obtain only one instance of the SystemTray object
        SystemTray tray = SystemTray.getSystemTray();

        //If the icon is a file
        Image image = Toolkit.getDefaultToolkit().createImage("icon.png");
        //Alternative (if the icon is on the classpath):
        //Image image = Toolkit.getDefaultToolkit().createImage(getClass().getResource("icon.png"));

        TrayIcon trayIcon = new TrayIcon(image, "Tray Demo");
        //Let the system resize the image if needed
        trayIcon.setImageAutoSize(true);
        //Set tooltip text for the tray icon
        trayIcon.setToolTip("System tray icon demo");
        tray.add(trayIcon);

        trayIcon.displayMessage("Hello, World", "notification demo", TrayIcon.MessageType.INFO);
    }

}