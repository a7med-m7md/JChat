package Client.network;


import model.*;
import exceptions.UserNotFoundException;
import model.user.UserEntity;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientServices {
    public static UserEntity logIn(String phoneNumber, String password) throws UserNotFoundException, RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            return user.login(new LoginEntity(phoneNumber, password));

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
