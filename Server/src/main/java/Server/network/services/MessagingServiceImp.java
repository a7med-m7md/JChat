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
        ClientServices clientServices = ConnectedService.clients.get(msg.getReceiver());
        clientServices.receiveMessage(msg);
    }

    @Override
    public void sendGroupMessage(GroupMessageEntity msg) throws RemoteException {
        System.out.println("From server processing:: " );
        msg.getList().stream().forEach(member->{
           if(member.getUserMobile() != msg.getSender() && ConnectedService.clients.containsKey(member.getUserMobile())){
               System.out.println("User Group online");
               try {
                   ConnectedService.clients.get(member.getUserMobile()).receiveMessageFromGroup(msg);
               } catch (RemoteException e) {
                   e.printStackTrace();
               }
           }
        });
    }

}
