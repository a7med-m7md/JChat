package Server.network.services;

import Server.business.services.ConnectedService;
import model.MessageEntity;
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
}