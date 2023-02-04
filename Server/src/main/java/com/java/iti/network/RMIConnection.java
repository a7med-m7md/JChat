package com.java.iti.network;
import com.java.iti.network.interfaces.CheckLogin;
import com.java.iti.network.interfaces.ClientInt;
import com.java.iti.network.interfaces.ServerInt;
import com.java.iti.network.interfaces.LoginEntity;

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
    public RMIConnection(){
        try {
            registry = LocateRegistry.createRegistry(2233);
            System.out.println("RMI connection available on PORT 2333");
            //client.add(c);
//            try {
//                Naming.rebind("rmi://localhost:2233/startconnect", serverInt);
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
        } catch (RemoteException e) {
            System.out.println("Can't connect");
            e.printStackTrace();
        }
    }

    public void startServices(){
        try {
            CheckLogin checkLogin = new CheckLogin();
            registry.bind("rmi://localhost:2233/loginService", checkLogin);
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
//        try {
//            registry.unbind("rmi://localhost:2233/loginService");
//            UnicastRemoteObject.unexportObject(serverInt, true);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        } catch (NotBoundException e) {
//            e.printStackTrace();
//        }
    }
}
