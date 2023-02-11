package Server.persistance.dao;

import Server.persistance.ConnectionManager;
import model.FriendEntity;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserFriendDao implements UserFriendDaoInt{
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final Connection connection = connectionManager.getConnection();

    // get the user with this phone number
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


    public FriendEntity searchByMobileNum(String myMobileNum) throws SQLException, RemoteException {
        final String SQL = "SELECT * FROM jtalk.users WHERE mobile = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1, myMobileNum);
            ResultSet rs =preparedStatement.executeQuery();
            if(rs.next()){
                FriendEntity friend = new FriendEntity(
                        rs.getString("mobile"),
                        rs.getString("name"),
                        rs.getString("bio"),
                        rs.getString("status"));
                System.out.println(friend.getMobile());
                return friend;
            }
        }
        return null;
    }


    // Save the sending request to database
    @Override
    public void addToFriendList(String myMobileNum, String friendMobileNum) {
        final String SQL = "INSERT INTO jtalk.friendships(receiver_mobile, sender_mobile, status) VALUES (?,?,?) ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1, myMobileNum);
            preparedStatement.setString(2, friendMobileNum);
            preparedStatement.setString(3, "PENDING");
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get the user list of incoming requests
    @Override
    public List<FriendEntity> getFriendRequests(String myMobileNum) throws RemoteException, SQLException {
        final String SQL = "SELECT * FROM jtalk.users, jtalk.friendships " +
                "WHERE receiver_mobile = sender_mobile AND " +
                "receiver_mobile = ? AND status = 'PENDING'";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1, myMobileNum);
            List<FriendEntity> requests = new ArrayList<>();
            ResultSet resultSet =preparedStatement.executeQuery();

            while(resultSet.next()){
                FriendEntity friend = new FriendEntity();
                requests.add(createUser(resultSet, friend));
            }
            return requests;
        }
    }

    // Helper function to create FriendEntity
    private FriendEntity createUser(ResultSet resultSet, FriendEntity friend) {
        try {
            friend.setName(resultSet.getString("name"));
            friend.setBio(resultSet.getString("bio"));
            friend.setMobile(resultSet.getString("mobile"));
//            friend.setStatus(resultSet.getString("friendships.status"));
            friend.setUserPhoto(resultSet.getBytes("picture"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friend;
    }

    // reject user request
    @Override
    public void deleteRequest(String myMobileNum, String rejectedNum) throws RemoteException {
        final String SQL = "DELETE FROM friendships WHERE receiver_mobile = ? AND sender_mobile = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1, myMobileNum);
            preparedStatement.setString(2, rejectedNum);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Accept user request
    @Override
    public void acceptRequest(String myMobileNum, String friendNum) throws RemoteException {
        final String SQL = "UPDATE friendships SET status = ? WHERE receiver_mobile = ? AND sender_mobile = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1, "ACCEPTED");
            preparedStatement.setString(2, myMobileNum);
            preparedStatement.setString(3, friendNum);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Need to be checked more
    @Override
    public List<FriendEntity> getFriendList(String myMobileNum) throws SQLException, RemoteException {
        System.out.println("FriendList:: ");
//        final String SQL = "SELECT * FROM jtalk.users as u1, jtalk.friendships as f, jtalk.u2 " +
//                "WHERE mobile = receiver_mobile " +
//                "AND mobile = ? AND f.status = 'accepted'" +
//                "AND u1.mobile = u2.mobile";
//        final String SQL = "SELECT * FROM jtalk.users WHERE users.mobile IN (SELECT receiver_mobile FROM  jtalk.friendships" +
//                "WHERE friendships.receiver_mobile = '011014' AND friendships.status = 'ACCEPTED'" +
//                "    union SELECT sender_mobile FROM jtalk.friendships WHERE user.mobile = '011014' AND friendships.status = 'ACCEPTED'" +
//                ");";
        final String SQL = "SELECT * FROM jtalk.users WHERE users.mobile IN (SELECT receiver_mobile FROM  jtalk.friendships " +
                "WHERE friendships.receiver_mobile = ? AND friendships.status = 'ACCEPTED' " +
                "    union SELECT sender_mobile FROM jtalk.friendships WHERE  friendships.status = 'ACCEPTED'" +
                ") AND users.mobile <> ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
            preparedStatement.setString(1, myMobileNum);
            preparedStatement.setString(2, myMobileNum);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("AFTER");
            List<FriendEntity> friends = new ArrayList<>();
            System.out.println("LENT:: " + friends.size());
            while(resultSet.next()){
                FriendEntity friend = new FriendEntity();
                friends.add(createUser(resultSet, friend));
            }
            return friends;
        }
    }
}
