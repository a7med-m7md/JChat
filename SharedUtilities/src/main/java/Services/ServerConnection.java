package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

// check and set status of client
public interface ServerConnection extends Remote {
    boolean connected(ClientServices client) throws RemoteException;
    boolean disconnected(ClientServices client) throws RemoteException;
    boolean isConnected(String mobile) throws RemoteException;
}
