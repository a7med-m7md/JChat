package Client.network;


import Models.LoginEntity;
import Models.Message;
import Models.ClientInt;
import Models.ServerInt;
import Server.business.model.user.Gender;
import Server.business.model.user.UserStatus;
import Server.persistance.entities.UserEntity;
import exceptions.UserNotFoundException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {
    public static UserEntity logIn(String phoneNumber, String password) throws UserNotFoundException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            user.login(new LoginEntity(phoneNumber, password));
            return new UserEntity(
                    "010234567",
                    "Ahmed",
                    "new@gmail.com",
                    "",
                    "123456789",
                    Gender.MALE,
                    "Egypt",
                    "1999-02-20",
                    "Hello",
                    UserStatus.AVAILABLE
            );

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
