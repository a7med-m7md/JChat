package Client.network.services;

import Client.ui.models.CurrentUserAccount;
import model.FriendEntity;
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
    public String friendRequestNotification(FriendEntity friend) throws RemoteException {
        System.out.println("Notification from : " + friend.getName() + " of Mobile : " + friend.getMobile());
        return null;
    }


}
