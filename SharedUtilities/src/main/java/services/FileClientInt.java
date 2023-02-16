package services;

import model.FileEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileClientInt extends Remote {
    void receiveServerState(boolean serverState) throws RemoteException;
}
