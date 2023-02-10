package model;

import exceptions.DuplicateUserException;
import exceptions.UserNotFoundException;
import model.group.GroupEntity;
import model.user.UserEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInt extends Remote {
    void checkUserExists(String phoneNumber) throws RemoteException, UserNotFoundException;
    UserEntity login(LoginEntity name) throws RemoteException, UserNotFoundException;
    public void checkDuplicateUser(String phoneNumber) throws RemoteException, DuplicateUserException;
    void signUp(UserEntity userEntity) throws RemoteException , DuplicateUserException;
    String logout(String name) throws RemoteException;
    String connect(ClientInt client) throws RemoteException;
    String disconnect(ClientInt client) throws RemoteException;
    GroupEntity createGroup(GroupEntity entity) throws RemoteException;
    List<GroupEntity> getUserGroups(int userId) throws RemoteException;
}
