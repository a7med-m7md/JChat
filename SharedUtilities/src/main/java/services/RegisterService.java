package services;

import model.user.UserDto;
import model.user.UserEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegisterService extends Remote {
    boolean isNewUser(int id) throws RemoteException;
    boolean isNewUser(String phone) throws RemoteException;


    UserEntity register(UserDto userDto) throws RemoteException;
}
