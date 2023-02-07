package Server.persistance.dao;

import Server.persistance.entities.FriendEntity;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public interface UserFriendDaoInt {
    int searchByMobileNum(String myMobileNum, String friendMobileNum) throws SQLException, RemoteException;

    List<FriendEntity> getFriendRequests(String myMobileNum) throws RemoteException, SQLException;

    void deleteRequest(String myMobileNum, String rejectedNum) throws RemoteException;

    void acceptRequest(String myMobileNum, String friendNum) throws RemoteException;

    List<FriendEntity> getFriendList(String myMobileNum) throws SQLException, RemoteException;

    void addToFriendList(String myMobileNum, String FriendPhoneNo) throws SQLException, RemoteException;
}
