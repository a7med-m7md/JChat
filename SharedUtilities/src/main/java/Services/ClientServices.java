package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

// Client Info
public interface ClientServices extends Remote {
    String getMobile() throws RemoteException;
}
