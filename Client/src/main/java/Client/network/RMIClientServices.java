package Client.network;



import Client.network.services.ClientServiceFactory;
import Client.network.services.ClientServicesImp;
import exceptions.DuplicateUserException;
import model.*;
import exceptions.UserNotFoundException;
import model.user.UserDto;
import model.user.UserEntity;
import model.user.UserStatus;
import services.*;

import javax.security.auth.login.CredentialException;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.List;

public class RMIClientServices {

    static String ip = "192.168.1.5";
    static ServerInt user;
    public static UserEntity logIn(String phoneNumber, String password) throws UserNotFoundException, RemoteException {
        try {
            ServerInt user = (ServerInt)Naming.lookup("rmi://" + ip + ":2233/loginService");
            return user.login(new LoginEntity(phoneNumber, password));
        } catch (NotBoundException | MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static List<FriendEntity> loadFriends(String phoneNumber) throws RemoteException {
        try {
            ServerInt user = (ServerInt) Naming.lookup("rmi://" + ip + ":2233/loginService");
            return user.getAllFriends(phoneNumber);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<FriendEntity> loadFriendsRequest(String phoneNumber) throws RemoteException {
        try {
            ServerInt user = (ServerInt) Naming.lookup("rmi://" + ip + ":2233/loginService");
            return user.getAllFriendsRequest(phoneNumber);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void checkUserExists(String phoneNumber) throws UserNotFoundException {
        try {
            ServerInt user = (ServerInt) Naming.lookup("rmi://" + ip + ":2233/loginService");
            user.checkUserExists(phoneNumber);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void checkDuplicateUser(String phoneNumber) throws DuplicateUserException {
        try {
            ServerInt user = (ServerInt) Naming.lookup("rmi://" + ip + ":2233/loginService");
            user.checkDuplicateUser(phoneNumber);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public static FriendEntity searchFriend(String number) throws RemoteException {
        try {
            ChatService user = (ChatService) Naming.lookup("rmi://" + ip + ":2233/friendRequest");
            return user.searchFriend(number);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void tellMyStatus(String number, UserStatus status) throws RemoteException {
//        System.out.println("Telling ......");
//        try {
//            ChatService user = (ChatService) Naming.lookup("rmi://" + ip + ":2233/friendRequest");
//            user.tellMyStatusToFriends(number, status);
//        } catch (NotBoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
    }


    public static void sendFriendRequest(String sender, List<String> receivers) throws UserNotFoundException, RemoteException, SQLException {
        try {
            ChatService user = (ChatService) Naming.lookup("rmi://" + ip + ":2233/friendRequest");
            user.friendRequest(sender, receivers);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void acceptFriendRequest(String myNumber, String requestNumber) throws RemoteException {
        try {
            ChatService user = (ChatService) Naming.lookup("rmi://" + ip + ":2233/friendRequest");
            user.acceptFriendRequest(myNumber, requestNumber);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void rejectFriendRequest(String myNumber, String requestNumber) throws RemoteException {
        try {
            ChatService user = (ChatService) Naming.lookup("rmi://" + ip + ":2233/friendRequest");
            user.rejectFriendRequest(myNumber, requestNumber);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public static void registerInServer() {
        System.out.println("Register");
        try {
            ServerConnection server = (ServerConnection) Naming.lookup("rmi://" + ip + ":2233/connectedService");
            ClientServices clientServices = new ClientServicesImp();
            server.connected(clientServices);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static Group createGroup(Group group) throws RemoteException {
//        try {
//            ServerInt user = (ServerInt) Naming.lookup("rmi://" + ip + ":2233/loginService");
//            return user.createGroup(new Group(group.getName(), group.getDescription(), group.getOwner_mobile()));
//
//        } catch (NotBoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        return null;
    }

    public static List<GroupMember> getUsersInGroup(int userId) throws RemoteException {
//        try {
//            ServerInt user = (ServerInt) Naming.lookup("rmi://" + ip + ":2233/loginService");
//            return user.getUsersInGroup(userId);
//
//        } catch (NotBoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        return null;
    }


    public static List<Group> getAllMyGroups(String mobile) throws RemoteException {
//        try {
//            ServerInt user = (ServerInt) Naming.lookup("rmi://" + ip + ":2233/loginService");
//            return user.getAllMyGroups(mobile);
//
//        } catch (NotBoundException e) {
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        return null;
    }



    public static void addGroupMembers(List<GroupMember> members) throws RemoteException {
//        try {
//            ServerInt group = (ServerInt) Naming.lookup("rmi://" + ip + ":2233/loginService");
//            group.addGroupMembers(members);
//        } catch (NotBoundException | MalformedURLException e) {
//            e.printStackTrace();
//        }
    }




    public static UserEntity signUp(UserDto userObject) throws DuplicateUserException {
        try {
            RegisterService user = (RegisterService) Naming.lookup("rmi://" + ip + ":2233/register");
            return user.register(userObject);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void chatMessaging(MessageEntity msg) throws RemoteException {
        try {
            MessagingService user = (MessagingService) Naming.lookup("rmi://" + ip + ":2233/chatMessaging");
            user.sendMessage(msg);
        } catch (NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void groupMessaging(GroupMessageEntity msg) throws RemoteException {
        try {
            System.out.println("Message group send");
            MessagingService user = (MessagingService) Naming.lookup("rmi://" + ip + ":2233/chatMessaging");
            user.sendGroupMessage(msg);
        } catch (NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void updateInfo(UserDto userDto) throws RemoteException{
        try {
            ServerInt user = (ServerInt) Naming.lookup("rmi://" + ip + ":2233/loginService");
            user.updateProfile(userDto);
        } catch (NotBoundException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void logOut(String mobile) throws RemoteException{
        try {
            user = (ServerInt) Naming.lookup("rmi://" + ip + ":2233/loginService");
            user.logout(mobile);
        } catch (NotBoundException | CredentialException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() throws MalformedURLException, RemoteException {
        UnicastRemoteObject.unexportObject(ClientServiceFactory.getClientServiceImp(),true);
//        Registry registry = LocateRegistry.getRegistry();
//        try {
//            registry.unbind("rmi://" + ip + ":2233/chatMessaging");
//            registry.unbind("rmi://" + ip + ":2233/register");
//            registry.unbind("rmi://" + ip + ":2233/connectedService");
//            registry.unbind("rmi://" + ip + ":2233/friendRequest");
//            registry.unbind("rmi://" + ip + ":2233/loginService");
//        } catch (NotBoundException e) {
//            System.out.println("Already unbind");
//        } catch (ConnectException c){
//            System.out.println("Close..!");
//        }

    }

}
