package com.java.iti.utils.interfaces;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class LoginTest  extends UnicastRemoteObject implements ServerInt {

    List<ClientInt> clientList = new ArrayList<>();

    public LoginTest() throws RemoteException {
    }

    @Override
    public String login(String name) throws RemoteException {
        clientList.get(0).receiveMSG("01024251210", "Hello");
        return name + " logged in successfully";
    }

    @Override
    public String logout(String name) throws RemoteException {
        return name + " logged out";
    }

    @Override
    public String connect(ClientInt client) throws RemoteException {
        clientList.add(client);
        System.out.println("Connected");
        return null;
    }

    @Override
    public String disconnect(ClientInt client) throws RemoteException {
        clientList.remove(client);
        System.out.println("Disconnected");
        return null;
    }

}
