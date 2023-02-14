package services;

import exceptions.DuplicateUserException;
import exceptions.UserNotFoundException;
import model.FriendEntity;
import model.GroupMember;
import model.LoginEntity;
import model.user.UserDto;
import model.user.UserEntity;
import model.user.UserStatus;
import services.ClientInt;

import javax.security.auth.login.CredentialException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ServerInt extends Remote {
    void checkUserExists(String phoneNumber) throws RemoteException, UserNotFoundException;
    UserEntity login(LoginEntity name) throws RemoteException, UserNotFoundException;
    void checkDuplicateUser(String phoneNumber) throws RemoteException, DuplicateUserException;
    UserEntity signUp(UserDto userEntity) throws RemoteException , DuplicateUserException;
    String logout(String mobile) throws RemoteException, CredentialException;
    String connect(ClientInt client) throws RemoteException;
    String disconnect(ClientInt client) throws RemoteException;
    List<FriendEntity> getAllFriends(String mobile) throws RemoteException;
    List<FriendEntity> getAllFriendsRequest(String mobile) throws RemoteException;
    GroupEntity createGroup(GroupEntity entity) throws RemoteException;
    List<GroupMember> getUsersInGroup(int userId) throws RemoteException;
    void addGroupMembers(List<GroupMember> members) throws RemoteException;
    List<GroupEntity> getAllMyGroups(String mobile) throws RemoteException;
    boolean updateProfile(UserDto user) throws RemoteException;
}
