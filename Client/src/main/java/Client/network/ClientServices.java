package Client.network;


import Client.model.group.Group;
import model.*;
import exceptions.UserNotFoundException;
import model.group.GroupEntity;
import model.user.UserEntity;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

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
    public static GroupEntity createGroup(Group group) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/creategroup");
            return user.createGroup(new GroupEntity(group.getName(),group.getDescription(),group.getOwner_id()));

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<GroupEntity> getUserGroups(int userId) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/getusergroups");
            return user.getUserGroups(userId);

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
