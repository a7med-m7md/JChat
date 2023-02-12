package services;

import exceptions.UserNotFoundException;
import model.FriendEntity;
import model.user.UserStatus;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface ChatService extends Remote {
    List<FriendEntity> friendRequest(String sender, List<String> receivers) throws RemoteException, SQLException;
    void acceptFriendRequest(String myNumber, String requestNumber) throws RemoteException;
    void rejectFriendRequest(String myNumber, String requestNumber) throws RemoteException;
    FriendEntity searchFriend(String mobile) throws RemoteException;
    void tellMyStatusToFriends(String myNumber, UserStatus status) throws RemoteException;
}
