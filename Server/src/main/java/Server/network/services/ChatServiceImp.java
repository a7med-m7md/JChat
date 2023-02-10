package Server.network.services;

import Server.business.services.ConnectedService;
import Server.persistance.dao.UserFriendDao;
import exceptions.UserNotFoundException;
import model.FriendEntity;
import services.ChatService;
import services.ClientServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

public class ChatServiceImp extends UnicastRemoteObject implements ChatService {
    public ChatServiceImp() throws RemoteException {
    }

    @Override
    public void friendRequest(String sender, String receiver) throws RemoteException {
        UserFriendDao friendDao = new UserFriendDao();
        System.out.println("Request send to: " + receiver);
        try {
            int friend = friendDao.searchByMobileNum(sender, receiver);
            if(friend == 0) {
                System.out.println("User not found");
                return;
            }
            ClientServices clientServices = ConnectedService.clients.get(receiver);
            FriendEntity friendEntity = new FriendEntity();
            friendEntity.setName("KKK");
            friendEntity.setMobile(sender);
            clientServices.friendRequestNotification(friendEntity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
