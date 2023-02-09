package Client.network;


import Client.model.user.User;
import Models.*;
import exceptions.UserDuplicatedException;
import exceptions.UserNotFoundException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {
    public static void logIn(String phoneNumber, String password) throws UserNotFoundException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            user.login(new LoginEntity(phoneNumber, password));

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void signIn(User userObject) throws UserDuplicatedException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/register");
//           user.signIn(new UserEntity( new UserEntity(userObject.getMobile(),userObject.getName(),userObject.getEmail(),userObject.getPicture(),userObject.getPassword()
//                   ,userObject.getGender() , userObject.getCountry(),userObject.getDateOfBirth(),
//                   userObject.getBio(),userObject.getStatus())));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
