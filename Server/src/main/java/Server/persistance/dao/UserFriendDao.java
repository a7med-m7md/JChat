package Server.persistance.dao;

import Server.persistance.ConnectionManager;
import Server.persistance.entities.FriendEntity;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserFriendDao implements UserFriendDaoInt{
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final Connection connection = connectionManager.getConnection();
    @Override
    public int searchByMobileNum(String myMobileNum, String friendMobileNum) throws SQLException, RemoteException {
        final String SQL = "SELECT * FROM jtalk.users WHERE mobile = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1, friendMobileNum);
            ResultSet rs =preparedStatement.executeQuery();
            if(rs.next()){
                addToFriendList(myMobileNum, friendMobileNum);
                return 1;
            }
        }
        return 0;
    }

    private void addToFriendList(String myMobileNum, String friendMobileNum) {
        final String SQL = "INSERT INTO jtalk.friendships(reciever_id, sender_id, status) VALUES (?,?,?) ";
try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){

} catch (SQLException e) {
    e.printStackTrace();
}
    }

    @Override
    public List<FriendEntity> getFriendRequests(String myMobileNum) throws RemoteException, SQLException {
        return null;
    }

    @Override
    public void deleteRequest(String myMobileNum, String rejectedNum) throws RemoteException {

    }

    @Override
    public void acceptRequest(String myMobileNum, String friendNum) throws RemoteException {

    }

    @Override
    public List<FriendEntity> getFriendList(String myMobileNum) throws SQLException, RemoteException {
        return null;
    }

    @Override
    public void InsertInUserFriend(String myMobileNum, String FriendPhoneNo) throws SQLException, RemoteException {

    }
}
