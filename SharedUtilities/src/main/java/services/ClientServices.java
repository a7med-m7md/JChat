package services;

import model.FriendEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Client Info
public interface ClientServices extends Remote {
    String getMobile() throws RemoteException;
    String friendRequestNotification(FriendEntity friend) throws RemoteException;
}
