package model;

import exceptions.DuplicationUserException;
import exceptions.UserNotFoundException;
import model.group.GroupEntity;
import model.user.UserEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.DuplicateFormatFlagsException;
import java.util.List;

public interface ServerInt extends Remote {
    UserEntity login(LoginEntity name) throws RemoteException, UserNotFoundException;

    void signUp(UserEntity userEntity) throws RemoteException , DuplicationUserException;
    String logout(String name) throws RemoteException;
    String connect(ClientInt client) throws RemoteException;
    String disconnect(ClientInt client) throws RemoteException;
    GroupEntity createGroup(GroupEntity entity) throws RemoteException;
    List<GroupEntity> getUserGroups(int userId) throws RemoteException;
}
