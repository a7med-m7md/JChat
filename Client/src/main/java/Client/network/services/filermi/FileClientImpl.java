package Client.network.services.filermi;

import Client.network.services.filesocket.FileService;
import model.FileEntity;
import services.FileClientInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileClientImpl extends UnicastRemoteObject implements FileClientInt {
    long userId;

    public FileClientImpl(long userId) throws RemoteException {
        this.userId = userId;
    }

    @Override
    public void receiveServerState(boolean serverState) {
        if (serverState)
            FileService.getInstance().startConnection(this.userId);
        /*else
            FileService.getInstance().stopClient(this.userId);*/
    }
}
