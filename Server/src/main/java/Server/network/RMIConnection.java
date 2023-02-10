package Server.network;

import model.ClientInt;
import model.LoginEntity;
import model.Message;
import Server.network.services.user.CheckLogin;
import Server.network.group.CreateGroup;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class RMIConnection {
    private static final List<ClientInt> client = new ArrayList<>();
    Registry registry;
    LoginEntity serverInt;
    CheckLogin checkLogin;
    CreateGroup group;

    public RMIConnection() {
        try {
            registry = LocateRegistry.createRegistry(2233);
            System.out.println("RMI connection available on PORT 2333");
        } catch (RemoteException e) {
            System.out.println("Can't connect");
            e.printStackTrace();
        }
    }

    public void startServices() {
        try {
            checkLogin = new CheckLogin();
            registry.bind("rmi://localhost:2233/loginService", checkLogin);
//            group = new CreateGroup();
//            registry.bind("rmi://localhost:2233/getUserGroups", group);
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

    public void disconnect() {
        try {
            registry.unbind("rmi://localhost:2233/loginService");
            UnicastRemoteObject.unexportObject(checkLogin, true);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
