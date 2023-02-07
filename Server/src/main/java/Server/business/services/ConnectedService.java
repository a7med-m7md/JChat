package Server.business.services;

import Models.ClientInt;
import Models.LoginEntity;
import Models.ServerInt;
import Services.ClientServices;
import Services.ServerConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ConnectedService extends UnicastRemoteObject implements ServerConnection {
    ObservableMap<String, ClientServices> clients = FXCollections.observableHashMap();

    protected ConnectedService() throws RemoteException {
    }

    public ObservableMap<String, ClientServices> getClients() {
        return clients;
    }

    @Override
    public synchronized boolean connected(ClientServices client) throws RemoteException {
        clients.put(client.getMobile(), client);
        return false;
    }

    @Override
    public synchronized boolean isConnected(String mobile) throws RemoteException {
        return clients.containsKey(mobile);
    }

    @Override
    public synchronized boolean disconnected(ClientServices client) throws RemoteException {
        if(clients.containsKey(client.getMobile())){
            clients.remove(client);
            System.out.println("User " + client.getMobile() + " disconnected");
            return true;
        }
        return false;
    }



    public ClientServices getClientService(String client){
        return clients.get(client);
    }
}
