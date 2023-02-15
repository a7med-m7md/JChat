package Client.network.services.filermi;

import model.FileEntity;
import services.FileClientInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileClientImpl extends UnicastRemoteObject implements FileClientInt {

    public FileClientImpl() throws RemoteException {
    }

    @Override
    public boolean receiveServerState(boolean serverState) {
        return serverState;
    }
}
