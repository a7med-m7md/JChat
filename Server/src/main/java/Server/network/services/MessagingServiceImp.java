package Server.network.services;

import Server.business.services.ConnectedService;
import model.MessageEntity;
import model.GroupMessageEntity;
import services.ClientServices;
import services.MessagingService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessagingServiceImp extends UnicastRemoteObject implements MessagingService {
    public MessagingServiceImp() throws RemoteException {
    }

    @Override
    public void sendMessage(MessageEntity msg) throws RemoteException {
        if(ConnectedService.clients.containsKey(msg.getReceiver())) {
            ClientServices clientServices = ConnectedService.clients.get(msg.getReceiver());
            clientServices.receiveMessage(msg);
        }else {
            System.out.println("Offline user");
        }
    }

    @Override
    public void sendGroupMessage(GroupMessageEntity msg) throws RemoteException {
        msg.getList().stream().forEach(member -> {
            if (!(member.getMobile().equals(msg.getSender())) && ConnectedService.clients.containsKey(member.getMobile())) {
                try {
                    ConnectedService.clients.get(member.getMobile()).receiveMessageFromGroup(msg);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
