package services;

import model.FileEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileClientInt extends Remote {
    boolean receiveServerState(boolean serverState) throws RemoteException;
}
