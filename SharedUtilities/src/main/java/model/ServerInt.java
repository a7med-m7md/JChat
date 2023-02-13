package model;

import exceptions.DuplicateUserException;
import exceptions.UserNotFoundException;
import model.group.GroupEntity;
import model.user.UserDto;
import model.user.UserEntity;
import model.user.UserStatus;

import javax.security.auth.login.CredentialException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInt extends Remote {
    void checkUserExists(String phoneNumber) throws RemoteException, UserNotFoundException;
    UserEntity login(LoginEntity name) throws RemoteException, UserNotFoundException;
    public void checkDuplicateUser(String phoneNumber) throws RemoteException, DuplicateUserException;
    UserEntity signUp(UserDto userEntity) throws RemoteException , DuplicateUserException;
    String logout(String mobile, UserStatus status) throws RemoteException, CredentialException;
    String connect(ClientInt client) throws RemoteException;
    String disconnect(ClientInt client) throws RemoteException;
    List<FriendEntity> getAllFriends(String mobile) throws RemoteException;
    List<FriendEntity> getAllFriendsRequest(String mobile) throws RemoteException;
    GroupEntity createGroup(GroupEntity entity) throws RemoteException;
    List<GroupEntity> getUserGroups(int userId) throws RemoteException;
}
