package Client.network;



import Client.network.services.ClientServicesImp;
import exceptions.DuplicateUserException;
import model.*;
import exceptions.UserNotFoundException;
import model.user.UserDto;
import model.user.UserEntity;
import model.user.UserStatus;
import services.*;

import javax.security.auth.login.CredentialException;
import java.io.File;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.List;

public class RMIClientServices {
    static Registry chatRegistry;

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


    public static boolean getServerState(){
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            FileServerInt fileServerInt = (FileServerInt) registry.lookup("rmi://localhost:2233/filetransfer");
            return fileServerInt.getServerState();
        }catch (RemoteException | NotBoundException ex){
            ex.printStackTrace();
        }
        return false;
    }


    public static void registerClientOnFileServer(FileClientInt fileClientInt){
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            FileServerInt fileServerInt = (FileServerInt) registry.lookup("rmi://localhost:2233/filetransfer");
            //fromFileToByte(file);
            //fileServerInt.uploadFileToServer(file,receiverId);
            fileServerInt.register(fileClientInt);
        }catch (RemoteException | NotBoundException ex){
            ex.printStackTrace();
        }
    }

    public static void unRegisterClientOnFileServer(FileClientInt fileClientInt){
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            FileServerInt fileServerInt = (FileServerInt) registry.lookup("rmi://localhost:2233/filetransfer");
            //fromFileToByte(file);
            //fileServerInt.uploadFileToServer(file,receiverId);
            fileServerInt.unRegister(fileClientInt);
        }catch (RemoteException | NotBoundException ex){
            ex.printStackTrace();
        }
    }

    /*private static byte[] fromFileToByte(File file) {
        byte[] res= null;
        try{
            res = new byte[(int) file.length ()];
            Bufferedinputstream is = new Bufferedinputstream (new FileInputStream (file));
            Is.read (res);
        }catch (NotBoundException exc){
            exc.printStackTrace();
        }
    }*/


    public static List<FriendEntity> loadFriends(String phoneNumber) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            return user.getAllFriends(phoneNumber);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<FriendEntity> loadFriendsRequest(String phoneNumber) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            return user.getAllFriendsRequest(phoneNumber);
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
        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void checkDuplicateUser(String phoneNumber) throws DuplicateUserException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            user.checkDuplicateUser(phoneNumber);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


    public static FriendEntity searchFriend(String number) throws RemoteException {
        try {
            if (chatRegistry == null) {
                chatRegistry = LocateRegistry.getRegistry(2233);
            }
            ChatService user = (ChatService) chatRegistry.lookup("rmi://localhost:2233/friendRequest");
            return user.searchFriend(number);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void tellMyStatus(String number, UserStatus status) throws RemoteException {
        System.out.println("Telling ......");
        try {
            if (chatRegistry == null) {
                chatRegistry = LocateRegistry.getRegistry(2233);
            }
            ChatService user = (ChatService) chatRegistry.lookup("rmi://localhost:2233/friendRequest");
            user.tellMyStatusToFriends(number, status);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }


    public static void sendFriendRequest(String sender, List<String> receivers) throws UserNotFoundException, RemoteException, SQLException {

        try {
            if (chatRegistry == null) {
                chatRegistry = LocateRegistry.getRegistry(2233);
            }
            ChatService user = (ChatService) chatRegistry.lookup("rmi://localhost:2233/friendRequest");
            user.friendRequest(sender, receivers);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static void acceptFriendRequest(String myNumber, String requestNumber) throws RemoteException {
        try {
            if (chatRegistry == null) {
                chatRegistry = LocateRegistry.getRegistry(2233);
            }
            ChatService user = (ChatService) chatRegistry.lookup("rmi://localhost:2233/friendRequest");
            user.acceptFriendRequest(myNumber, requestNumber);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static void rejectFriendRequest(String myNumber, String requestNumber) throws RemoteException {
        try {
            if (chatRegistry == null) {
                chatRegistry = LocateRegistry.getRegistry(2233);
            }
            ChatService user = (ChatService) chatRegistry.lookup("rmi://localhost:2233/friendRequest");
            user.rejectFriendRequest(myNumber, requestNumber);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }


    public static void registerInServer() {
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

    public static Group createGroup(Group group) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            return user.createGroup(group);

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<GroupMember> getUsersInGroup(int userId) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            return user.getUsersInGroup(userId);

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Group> getAllMyGroups(String mobile) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            return user.getAllMyGroups(mobile);

        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void addGroupMembers(List<GroupMember> members) throws RemoteException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt group = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            group.addGroupMembers(members);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }




    public static UserEntity signUp(UserDto userObject) throws DuplicateUserException {
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            RegisterService user = (RegisterService) registry.lookup("rmi://localhost:2233/register");
            return user.register(userObject);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void chatMessaging(MessageEntity msg) throws RemoteException {
        Registry messagingRegistry;
        try {
            chatRegistry = LocateRegistry.getRegistry(2233);
            MessagingService user = (MessagingService) chatRegistry.lookup("rmi://localhost:2233/chatMessaging");
            user.sendMessage(msg);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static void groupMessaging(GroupMessageEntity msg) throws RemoteException {
        Registry messagingRegistry;
        try {
            chatRegistry = LocateRegistry.getRegistry(2233);
            MessagingService user = (MessagingService) chatRegistry.lookup("rmi://localhost:2233/chatMessaging");
            user.sendGroupMessage(msg);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static void updateInfo(UserDto userDto) throws RemoteException{
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            user.updateProfile(userDto);
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public static void logOut(String mobile) throws RemoteException{
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt) registry.lookup("rmi://localhost:2233/loginService");
            user.logout(mobile);
        } catch (NotBoundException | CredentialException e) {
            e.printStackTrace();
        }
    }

}
