package services;

import model.FriendEntity;
import model.MessageEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Client Info
public interface ClientServices extends Remote {
    String getMobile() throws RemoteException;
    void friendRequestNotification(FriendEntity friend) throws RemoteException;
    void receiveMessage(MessageEntity msg) throws RemoteException;
    void receiveAnnouncement(String msg) throws RemoteException;
}
