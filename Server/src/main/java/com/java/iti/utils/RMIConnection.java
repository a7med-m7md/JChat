package com.java.iti.utils;
import com.java.iti.utils.interfaces.ServerInt;
import com.java.iti.utils.interfaces.LoginTest;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;

public class RMIConnection {
    public static void connect(){
        try {
            ServerInt serverInt = new LoginTest();
            LocateRegistry.createRegistry(2233);
            try {
                Naming.rebind("rmi://localhost:2233/startconnect", serverInt);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } catch (RemoteException e) {
            System.out.println("Can't connect");
            e.printStackTrace();
        }
    }
}
