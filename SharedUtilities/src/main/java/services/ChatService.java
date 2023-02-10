package services;

import exceptions.UserNotFoundException;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatService extends Remote {
    void friendRequest(String sender, String receiver) throws RemoteException;
}
