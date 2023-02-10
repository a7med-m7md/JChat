package model;

import exceptions.UserNotFoundException;
import model.user.UserEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInt extends Remote {
    UserEntity login(LoginEntity name) throws RemoteException, UserNotFoundException;
    String logout(String name) throws RemoteException;
    String connect(ClientInt client) throws RemoteException;
    String disconnect(ClientInt client) throws RemoteException;
}
