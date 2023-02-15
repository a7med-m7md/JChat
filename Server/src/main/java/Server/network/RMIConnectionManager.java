package Server.network;
import Server.business.services.ConnectedService;
import Server.business.services.register.RegisterServiceImpl;
import Server.network.services.ChatServiceImp;
import Server.network.services.MessagingServiceImp;
import Server.network.services.RMIServerServices;
import services.ServerInt;
import services.ChatService;
import services.MessagingService;
import services.RegisterService;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
            Naming.rebind("rmi://localhost:2233/loginService", rmiServerServices);

            connectedService = new ConnectedService();
            Naming.rebind("rmi://localhost:2233/connectedService", connectedService);

            chatService = new ChatServiceImp();
            System.out.println("Friend Request Started");
            Naming.rebind("rmi://localhost:2233/friendRequest", chatService);

            System.out.println("Chatting Service Started");
            messagingService = new MessagingServiceImp();
            Naming.rebind("rmi://localhost:2233/chatMessaging", messagingService);

            System.out.println("Register Service Started");
            registerService = new RegisterServiceImpl();
            Naming.rebind("rmi://localhost:2233/register", registerService);
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
            try {

                System.out.println("Close Friend Request");
                Naming.unbind("rmi://localhost:2233/friendRequest");
                UnicastRemoteObject.unexportObject(chatService, true);

                System.out.println("Close Chat Messaging");
                Naming.unbind("rmi://localhost:2233/chatMessaging");
                UnicastRemoteObject.unexportObject(messagingService, true);

                System.out.println("Close register");
                Naming.unbind("rmi://localhost:2233/register");
                UnicastRemoteObject.unexportObject(registerService, true);

                System.out.println("Close connected Services");
                Naming.unbind("rmi://localhost:2233/connectedService");
                UnicastRemoteObject.unexportObject(connectedService, true);

                System.out.println("Close login service");
                Naming.unbind("rmi://localhost:2233/loginService");
                UnicastRemoteObject.unexportObject(rmiServerServices, true);


            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                System.out.println("Already closed!");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
    }

}
