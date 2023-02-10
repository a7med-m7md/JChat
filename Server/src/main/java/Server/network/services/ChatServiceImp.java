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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatServiceImp extends UnicastRemoteObject implements ChatService {
    public ChatServiceImp() throws RemoteException {
    }

    @Override
    public List<FriendEntity> friendRequest(String sender, List<String> receivers) throws RemoteException {
        UserFriendDao friendDao = new UserFriendDao();
        List<FriendEntity> requestLST = new ArrayList<>();
            receivers.stream().forEach(
                    (receiver)->{
                        System.out.println("Request send to: " + receiver);
                        try {
                            FriendEntity friendEntity = friendDao.searchByMobileNum(receiver);
                            requestLST.add(friendEntity);
                            ClientServices clientServices = ConnectedService.clients.get(receiver);
                            clientServices.friendRequestNotification(friendEntity);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
            );
            return requestLST;
        }
}
