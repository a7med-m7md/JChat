package services;

import exceptions.UserNotFoundException;
import model.FriendEntity;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatService extends Remote {
    List<FriendEntity> friendRequest(String sender, List<String> receivers) throws RemoteException;
    void acceptFriendRequest(String myNumber, String requestNumber) throws RemoteException;
    void rejectFriendRequest(String myNumber, String requestNumber) throws RemoteException;
}
