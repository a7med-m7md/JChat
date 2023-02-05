package Models;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Message extends UnicastRemoteObject implements ClientInt{
    public Message() throws RemoteException {
    }

    @Override
    public String receiveMSG(String mobNum, String msg) throws RemoteException{
        System.out.println("Mob: " + mobNum + " received: " + msg);

        return null;
    }
}
