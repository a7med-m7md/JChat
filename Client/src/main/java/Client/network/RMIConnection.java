package Client.network;


import Models.LoginEntity;
import Models.Message;
import Models.ClientInt;
import Models.ServerInt;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIConnection {
    public static void connect(){
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(2233);
            ServerInt user = (ServerInt)registry.lookup("rmi://localhost:2233/loginService");
            ClientInt clientInt = new Message();
            user.connect(clientInt);
            user.login(new LoginEntity("011014", "1234password"));
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
