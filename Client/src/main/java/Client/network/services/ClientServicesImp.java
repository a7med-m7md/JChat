package Client.network.services;


import Client.ui.components.ErrorMessageUi;
import Client.ui.controllers.MainController;
import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Duration;
import model.*;
import model.user.UserStatus;
import services.ClientServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

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
        MainController mainController = MainController.getInstance();
        ErrorMessageUi message = new ErrorMessageUi("New Friend Request!", false);
        mainController.currentUserPane.getChildren().add(message);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(mainController.currentUserPane.translateYProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            mainController.currentUserPane.getChildren().remove(message);
        });
        timeline.play();


    }

    @Override
    public void receiveMessage(MessageEntity msg) throws RemoteException {

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
                System.out.println("Msg send from: " + msg.getSender() + " Msg body: " + msg.getMessage());
                MainController mainController = MainController.getInstance();
                ErrorMessageUi message = new ErrorMessageUi("New Message From: " + senderContact.getName(), false);
                mainController.currentUserPane.getChildren().add(message);
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(mainController.currentUserPane.translateYProperty(), 0, Interpolator.EASE_BOTH);
                KeyFrame kf = new KeyFrame(Duration.seconds(3), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(t -> {
                    mainController.currentUserPane.getChildren().remove(message);
                });
                timeline.play();


                //Scrolls Down Automatically when new messages added
            }
        });
    }

    @Override
    public void receiveAnnouncement(String msg) throws RemoteException {
        Platform.runLater(new Runnable() {
                              @Override
                              public void run() {
                                  MainController mainController = MainController.getInstance();
                                  ErrorMessageUi message = new ErrorMessageUi(msg, false);
                                  mainController.currentUserPane.getChildren().add(message);
                                  Timeline timeline = new Timeline();
                                  KeyValue kv = new KeyValue(mainController.currentUserPane.translateYProperty(), 0, Interpolator.EASE_BOTH);
                                  KeyFrame kf = new KeyFrame(Duration.seconds(3), kv);
                                  timeline.getKeyFrames().add(kf);
                                  timeline.setOnFinished(t -> {
                                      mainController.currentUserPane.getChildren().remove(message);
                                  });
                                  timeline.play();
                              }
                          }
        );
    }

    @Override
    public void receiveFriendStatus(String mobile, UserStatus status) throws RemoteException {
        CurrentSession currentSession = CurrentSession.getInstance();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Contact changedStatusContact =
                        currentSession.getContactsList()
                                .stream()
                                .filter(contact -> contact.getMobile().equals(mobile))
                                .findFirst()
                                .get();
                changedStatusContact.setStatus(status);
            }
        });
    }

    @Override
    public void receiveMessageFromGroup(GroupMessageEntity msg) throws RemoteException {
        CurrentSession currentSession = CurrentSession.getInstance();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Group messageGroup = msg.getGroup();
                Optional<Group> myGroup = currentSession.groupChatsMapProperty().keySet().stream()
                        .filter(group -> group.getId() == messageGroup.getId())
                        .findAny();
                currentSession.groupChatsMapProperty().get(myGroup.get()).add(msg);

                MainController mainController = MainController.getInstance();
                ErrorMessageUi message = new ErrorMessageUi("New message from " + myGroup.get().getName(), true);
                mainController.currentUserPane.getChildren().add(message);
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(mainController.currentUserPane.translateYProperty(), 0, Interpolator.EASE_BOTH);
                KeyFrame kf = new KeyFrame(Duration.seconds(3), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(t -> {
                    mainController.currentUserPane.getChildren().remove(message);
                });
                timeline.play();

            }
        });

    }

    @Override
    public void receiveGroupAddNotification(Group group) throws RemoteException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ObservableList<GroupMessageEntity> newGroupMessageList = FXCollections.observableArrayList();
                CurrentSession.getInstance().groupChatsMapProperty().put(group, newGroupMessageList);

                MainController mainController = MainController.getInstance();
                ErrorMessageUi message = new ErrorMessageUi("Added to Group: " + group.getName(), false);
                mainController.currentUserPane.getChildren().add(message);
                Timeline timeline = new Timeline();
                KeyValue kv = new KeyValue(mainController.currentUserPane.translateYProperty(), 0, Interpolator.EASE_BOTH);
                KeyFrame kf = new KeyFrame(Duration.seconds(3), kv);
                timeline.getKeyFrames().add(kf);
                timeline.setOnFinished(t -> {
                    mainController.currentUserPane.getChildren().remove(message);
                });
                timeline.play();

            }
        });

    }


}