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
    static ChatService chatService;
    static ServerConnection serverConnection;
    static RegisterService userRegister;
    static MessagingService messagingService;

    static {
        try {
            user = (ServerInt) Naming.lookup("rmi://" + ip + ":2233/services");
            chatService = (ChatService) Naming.lookup("rmi://" + ip + ":2233/friendRequest");
            serverConnection = (ServerConnection) Naming.lookup("rmi://" + ip + ":2233/connectedService");
            userRegister = (RegisterService) Naming.lookup("rmi://" + ip + ":2233/register");
            messagingService = (MessagingService) Naming.lookup("rmi://" + ip + ":2233/chatMessaging");
        } catch (NotBoundException e) {
            System.out.println("Server unavailable");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public static UserEntity logIn(String phoneNumber, String password) throws UserNotFoundException, RemoteException {
        tellMyStatus(phoneNumber, UserStatus.AVAILABLE);
        return user.login(new LoginEntity(phoneNumber, password));
    }


    public static List<FriendEntity> loadFriends(String phoneNumber) throws RemoteException {
        return user.getAllFriends(phoneNumber);
    }


    public static List<FriendEntity> loadFriendsRequest(String phoneNumber) throws RemoteException {
        return user.getAllFriendsRequest(phoneNumber);
    }

    public static void checkUserExists(String phoneNumber) throws UserNotFoundException, RemoteException {
        user.checkUserExists(phoneNumber);
    }

    public static void checkDuplicateUser(String phoneNumber) throws DuplicateUserException, RemoteException {
        user.checkDuplicateUser(phoneNumber);
    }


    public static FriendEntity searchFriend(String number) throws RemoteException {
        return chatService.searchFriend(number);
    }


    public static void tellMyStatus(String number, UserStatus status) throws RemoteException {
        chatService.tellMyStatusToFriends(number, status);
    }


    public static void sendFriendRequest(String sender, List<String> receivers) throws UserNotFoundException, RemoteException, SQLException {
        chatService.friendRequest(sender, receivers);
    }

    public static void acceptFriendRequest(String myNumber, String requestNumber) throws RemoteException {
        chatService.acceptFriendRequest(myNumber, requestNumber);
    }

    public static void rejectFriendRequest(String myNumber, String requestNumber) throws RemoteException {
        chatService.rejectFriendRequest(myNumber, requestNumber);
    }


    public static void registerInServer() throws RemoteException {
        ClientServices clientServices = new ClientServicesImp();
        serverConnection.connected(clientServices);
//        System.out.println(LocateRegistry.getRegistry().list());
    }

    public static Group createGroup(Group group) throws RemoteException {
        return user.createGroup(new Group(group.getName(), group.getDescription(), group.getOwner_mobile()));
    }

    public static List<GroupMember> getUsersInGroup(int userId) throws RemoteException {
        return user.getUsersInGroup(userId);
    }


    public static List<Group> getAllMyGroups(String mobile) throws RemoteException {
        return user.getAllMyGroups(mobile);
    }



    public static void addGroupMembers(List<GroupMember> members) throws RemoteException {
        user.addGroupMembers(members);
    }




    public static UserEntity signUp(UserDto userObject) throws DuplicateUserException, RemoteException {
        return userRegister.register(userObject);
    }

    public static void chatMessaging(MessageEntity msg) throws RemoteException {
        messagingService.sendMessage(msg);
    }

    public static void groupMessaging(GroupMessageEntity msg) throws RemoteException {
        messagingService.sendGroupMessage(msg);
    }

    public static void updateInfo(UserDto userDto) throws RemoteException{
        user.updateProfile(userDto);
    }

    public static void logOut(String mobile) throws RemoteException{
        try {
            user.logout(mobile);
        } catch (CredentialException e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() throws MalformedURLException, RemoteException, NotBoundException {
//        UnicastRemoteObject.unexportObject(user, true);
//        UnicastRemoteObject.unexportObject(userRegister, true);
//        UnicastRemoteObject.unexportObject(chatService, true);
//        UnicastRemoteObject.unexportObject(serverConnection, true);
//        UnicastRemoteObject.unexportObject(messagingService, true);
//        System.out.println(LocateRegistry.getRegistry().list());
//        try {
//            Registry reg = LocateRegistry.getRegistry();
//            System.out.println(reg.list());
//            UnicastRemoteObject.unexportObject(reg, true);
//            reg.unbind("rmi://" + ip + ":2233/friendRequest");
//        } catch (NotBoundException e) {
//            System.out.println("Already closed!!");
//        }
//        UnicastRemoteObject.unexportObject(ClientServiceFactory.getClientServiceImp(),true);
//        Registry registry = LocateRegistry.getRegistry();
//        try {
//            registry.unbind("rmi://" + ip + ":2233/chatMessaging");
//            registry.unbind("rmi://" + ip + ":2233/register");
//            registry.unbind("rmi://" + ip + ":2233/connectedService");
//            registry.unbind("rmi://" + ip + ":2233/friendRequest");
//            registry.unbind("rmi://" + ip + ":2233/services");
//        } catch (NotBoundException e) {
//            System.out.println("Already unbind");
//        } catch (ConnectException c){
//            System.out.println("Close..!");
//        }

    }

}
