package Server.network.services;

import Server.business.services.ConnectedService;
import Server.persistance.dao.UserDao;
import Server.persistance.dao.UserFriendDao;
import exceptions.UserNotFoundException;
import model.FriendEntity;
import model.user.UserStatus;
import services.ChatService;
import services.ClientServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatServiceImp extends UnicastRemoteObject implements ChatService {
    UserFriendDao friendDao = new UserFriendDao();
    public ChatServiceImp() throws RemoteException {
    }

    @Override
    public List<FriendEntity> friendRequest(String sender, List<String> receivers) throws RemoteException, SQLException {
        List<FriendEntity> requestLST = new ArrayList<>();
        FriendEntity senderFriend = friendDao.searchByMobileNum(sender);
        receivers.stream().forEach(
                    (receiver)->{
                        System.out.println("Sender FRom : " + sender);
                        System.out.println("Request send to: " + receiver);
                        try {
                            // Search to
                            FriendEntity friendEntity = friendDao.searchByMobileNum(receiver);
                            requestLST.add(friendEntity);
                            friendDao.addToFriendList(receiver, sender);
                            ClientServices clientServices = ConnectedService.clients.get(receiver);
                            clientServices.friendRequestNotification(senderFriend);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                    }
            );

        System.out.println("Users of friend List:  ");
            requestLST.forEach(u->{
                System.out.println(u);
            });

            return requestLST;
        }

    @Override
    public void acceptFriendRequest(String myNumber, String requestNumber) throws RemoteException {
        friendDao.acceptRequest(myNumber, requestNumber);
    }

    @Override
    public void rejectFriendRequest(String myNumber, String requestNumber) throws RemoteException {
        friendDao.deleteRequest(myNumber, requestNumber);
    }

    @Override
    public FriendEntity searchFriend(String mobile) throws RemoteException {
        try {
            return friendDao.searchByMobileNum(mobile);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void tellMyStatusToFriends(String myNumber, UserStatus status) throws RemoteException {
        UserDao userDao = new UserDao();
        //1. Update my Status in DB
        userDao.updateUserStatus(myNumber, status);
        // todo make a method to update user status
        //2. Tell my status to friends
        List<FriendEntity> friends = friendDao.getFriendList(myNumber);
        System.out.println("List: " + friends);
        friends.forEach(friend->{
            if(ConnectedService.clients.containsKey(friend.getMobile())){
                try {
                    ConnectedService.clients.get(friend.getMobile()).receiveFriendStatus(myNumber, status);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void sendAnnouncementToUser(String msg) throws RemoteException {
        ConnectedService.clients.values().forEach(client->{
            try {
                client.receiveAnnouncement(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}
