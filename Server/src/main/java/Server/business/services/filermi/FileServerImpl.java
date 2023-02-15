package Server.business.services.filermi;

import services.ClientInt;
import services.FileClientInt;
import services.FileServerInt;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class FileServerImpl extends UnicastRemoteObject implements FileServerInt {
    public static List<FileClientInt> clients = new ArrayList<>();
    static boolean serverState = false;
    public FileServerImpl() throws RemoteException {
    }

    public static void setServerState(boolean serverState) {
        FileServerImpl.serverState = serverState;
    }

    @Override
    public void broadcastServerState(boolean serverState) throws RemoteException {
        for (FileClientInt temp:
             clients) {
            temp.receiveServerState(serverState);
        }
    }

    @Override
    public void register(FileClientInt fileClientInt) {
        clients.add(fileClientInt);
    }

    @Override
    public boolean getServerState() {
        return serverState;
    }

    @Override
    public void unRegister(FileClientInt fileClientInt) {
        clients.remove(fileClientInt);
    }



}
