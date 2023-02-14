package services;

import model.*;
import model.user.UserStatus;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Client Info
public interface ClientServices extends Remote {
    String getMobile() throws RemoteException;
    void friendRequestNotification(FriendEntity friend) throws RemoteException;
    void receiveMessage(MessageEntity msg) throws RemoteException;
    void receiveAnnouncement(String msg) throws RemoteException;
    void receiveFriendStatus(String mobile, UserStatus status) throws RemoteException;
    void receiveMessageFromGroup(GroupMessageEntity msg) throws RemoteException;
    void receiveGroupAddNotification(Group group) throws RemoteException;
}
