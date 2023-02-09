package Client.network;


import Models.LoginEntity;
import Models.Message;
import Models.ClientInt;
import Models.ServerInt;
import exceptions.UserNotFoundException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {
    public static void logIn(String phoneNumber, String password) throws UserNotFoundException, RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            user.login(new LoginEntity(phoneNumber, password));

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
