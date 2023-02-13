package Server.network;
import Server.business.services.ConnectedService;
import Server.business.services.register.RegisterServiceImpl;
import Server.network.services.ChatServiceImp;
import Server.network.services.MessagingServiceImp;
import model.ClientInt;
import model.LoginEntity;
import model.Message;
import Server.network.services.RMIServerServices;
import model.ServerInt;
import services.ChatService;
import services.MessagingService;
import services.RegisterService;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RMIConnectionManager {
    private static RMIConnectionManager instance = new RMIConnectionManager();
    Registry registry;
    ServerInt rmiServerServices;
    ChatService chatService;
    MessagingService messagingService;
    RegisterService registerService;
    ConnectedService connectedService;

    private RMIConnectionManager(){
        try {
            registry = LocateRegistry.createRegistry(2233);
            System.out.println("RMI connection available on PORT 2333");
        } catch (RemoteException e) {
            System.out.println("Can't connect");
            e.printStackTrace();
        }
    }


    public static RMIConnectionManager getInstance() {
        if(instance == null)
            instance = new RMIConnectionManager();
        return instance;
    }

    public void startServices(){
        try {
            rmiServerServices = new RMIServerServices();
            registry.rebind("rmi://localhost:2233/loginService", rmiServerServices);

            connectedService = new ConnectedService();
            registry.rebind("rmi://localhost:2233/connectedService", connectedService);

            chatService = new ChatServiceImp();
            System.out.println("Friend Request Started");
            registry.rebind("rmi://localhost:2233/friendRequest", chatService);
//
            System.out.println("Chatting Service Started");
            messagingService = new MessagingServiceImp();
            registry.rebind("rmi://localhost:2233/chatMessaging", messagingService);
//
            System.out.println("Register Service Started");
            registerService = new RegisterServiceImpl();
            registry.rebind("rmi://localhost:2233/register", registerService);
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
            try {
                System.out.println("Close login service");
                registry.unbind("rmi://localhost:2233/loginService");
                UnicastRemoteObject.unexportObject(rmiServerServices, true);

                System.out.println("Close Friend Request");
                registry.unbind("rmi://localhost:2233/friendRequest");
                UnicastRemoteObject.unexportObject(chatService, true);

                System.out.println("Close Chat Messaging");
                registry.unbind("rmi://localhost:2233/chatMessaging");
                UnicastRemoteObject.unexportObject(messagingService, true);
//
                System.out.println("Close register");
                registry.unbind("rmi://localhost:2233/register");
                UnicastRemoteObject.unexportObject(registerService, true);

                System.out.println("Close connected Services");
                registry.unbind("rmi://localhost:2233/connectedService");
                UnicastRemoteObject.unexportObject(connectedService, true);


            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
    }

    public void connected(){
//        try{
//
//        } catch (AccessException e) {
//            e.printStackTrace();
//        } catch (AlreadyBoundException e) {
//            e.printStackTrace();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

}
