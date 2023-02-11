package Client.network.services;

import Client.ui.models.Contact;
import Client.ui.models.CurrentSession;
import Client.ui.models.CurrentUserAccount;
import model.FriendEntity;
import model.MessageEntity;
import services.ClientServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientServicesImp extends UnicastRemoteObject implements ClientServices {
    public ClientServicesImp() throws RemoteException {
    }

    @Override
    public String getMobile() throws RemoteException {
        System.out.println("FROM User side");
        System.out.println(CurrentUserAccount.getMyAccount().getPhoneNumber());
        return CurrentUserAccount.getInstance().getPhoneNumber();
    }

    @Override
    public void friendRequestNotification(FriendEntity friend) throws RemoteException {
        CurrentSession currentSession= CurrentSession.getInstance();
        currentSession.requestsListProperty().add(new Contact(friend));
    }

    @Override
    public void receiveMessage(MessageEntity msg) throws RemoteException {
        System.out.println("Msg send from: " + msg.getSender() + " Msg body: " + msg.getMSGBody());
    }


}
