package Server.network;
import Server.business.services.ConnectedService;
import model.ClientInt;
import model.LoginEntity;
import model.Message;
import Server.network.services.RMIServerServices;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIConnectionManager {
    private static final List<ClientInt> client = new ArrayList<>();
    Registry registry;
    LoginEntity serverInt;
    RMIServerServices checkLogin;
    public RMIConnectionManager(){
        try {
            registry = LocateRegistry.createRegistry(2233);
            System.out.println("RMI connection available on PORT 2333");
        } catch (RemoteException e) {
            System.out.println("Can't connect");
            e.printStackTrace();
        }
    }

    public void startServices(){
        try {
            checkLogin = new RMIServerServices();
            registry.bind("rmi://localhost:2233/loginService", checkLogin);
            ClientInt clientInt = new Message();
            clientInt.receiveMSG("01024251210", "Hello");
            System.out.println("Services started");
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            registry.unbind("rmi://localhost:2233/loginService");
            UnicastRemoteObject.unexportObject(checkLogin, true);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void connected(){
        try{
            // Waiting for any user to connect
            ConnectedService connectedService = new ConnectedService();
            registry.bind("rmi://localhost:2233/connectedService", connectedService);
        } catch (AccessException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
