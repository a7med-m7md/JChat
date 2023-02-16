package services;

import model.FileEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileServerInt extends Remote {
    public void broadcastServerState(boolean serverState) throws RemoteException;
    public void register(FileClientInt fileClientInt) throws RemoteException;
    public void unRegister(FileClientInt fileClientInt) throws RemoteException;
    public boolean getServerState() throws RemoteException;

}
