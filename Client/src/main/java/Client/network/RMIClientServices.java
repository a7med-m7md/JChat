package Client.network;


import Client.model.group.Group;
import Client.network.services.ClientServicesImp;
import exceptions.DuplicateUserException;
import model.*;
import exceptions.UserNotFoundException;
import model.group.GroupEntity;
import model.user.UserEntity;
import services.ChatService;
import services.ClientServices;
import services.ServerConnection;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class RMIClientServices {
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
    public static void checkUserExists(String phoneNumber) throws UserNotFoundException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            user.checkUserExists(phoneNumber);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }  catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    public static void sendFriendRequest(String sender, List<String> receivers) throws UserNotFoundException, RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ChatService user = (ChatService) registry.lookup("rmi://localhost:2233/friendRequest");
            user.friendRequest(sender, receivers);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }


    public static void registerInServer(){
        System.out.println("Register");
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerConnection server = (ServerConnection) registry.lookup("rmi://localhost:2233/connectedService");
            ClientServices clientServices = new ClientServicesImp();
            server.connected(clientServices);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static GroupEntity createGroup(Group group) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/creategroup");
            return user.createGroup(new GroupEntity(group.getName(), group.getDescription(), group.getOwner_id()));

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<GroupEntity> getUserGroups(int userId) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            return user.getUserGroups(userId);

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    

    public static void signUp(UserEntity userObject) throws DuplicateUserException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/register");
            user.signUp(userObject);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
