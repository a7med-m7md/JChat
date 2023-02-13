package Server.network.services;

import Server.business.mappers.UseMapperImpl;
import Server.business.mappers.UserMapper;
import Server.business.model.group.Group;
import Server.business.services.ConnectedService;
import Server.business.services.login.LoginService;
import Server.business.services.login.LoginServiceImp;
import Server.business.services.register.RegisterServiceImpl;
import Server.persistance.dao.GroupDao;
import Server.persistance.dao.GroupMemberDao;
import Server.persistance.dao.UserFriendDao;
import exceptions.DuplicateUserException;
import model.*;
import exceptions.UserNotFoundException;
import Server.persistance.dao.UserDao;
import model.group.GroupEntity;
import model.user.UserDto;
import model.user.UserEntity;
import model.user.UserStatus;
import services.ClientInt;
import services.ServerInt;

import javax.security.auth.login.CredentialException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Optional;

public class RMIServerServices extends UnicastRemoteObject implements ServerInt {
    ConnectedService connectedService = new ConnectedService();

    public RMIServerServices() throws RemoteException {
    }

    @Override
    public UserEntity login(LoginEntity userInfo) throws UserNotFoundException {
        UserDao user = new UserDao();
        Optional<UserEntity> userEntity = user.userLogin(userInfo);
        user.updateUserStatus(userInfo.getMobile(), UserStatus.AVAILABLE);
        if (userEntity.isPresent()) {
            System.out.println("Logged in successfully");
//            connectedService.connected();
            return userEntity.orElse(null);
        } else {
            System.out.println("Can't login");
            throw new UserNotFoundException();
        }
    }

    @Override
    public void checkUserExists(String phoneNumber) throws UserNotFoundException {
        UserDao user = new UserDao();
        Optional<UserEntity> userEntity = user.findByMobile(phoneNumber);
        if (userEntity.isPresent()) {
            System.out.println("user exists");
        } else {
            System.out.println("User doesn't exist");
            throw new UserNotFoundException();
        }

    }

    @Override
    public void checkDuplicateUser(String phoneNumber) throws RemoteException, DuplicateUserException {
        UserMapper userMapper = new UseMapperImpl();
        RegisterServiceImpl registerService = new RegisterServiceImpl();
        if (!registerService.isNewUser(phoneNumber)) {
            throw new DuplicateUserException();
        }
    }

    @Override
    public UserEntity signUp(UserDto userDto) throws RemoteException {
//        UserMapper userMapper = new UseMapperImpl();
        RegisterServiceImpl registerService = new RegisterServiceImpl();
        return registerService.register(userDto);
    }


    @Override
    public String logout(String mobile) throws RemoteException, CredentialException {
        LoginService loginService = new LoginServiceImp();
        loginService.logOut(mobile);
        return "LogOut Successfully";
    }

    @Override
    public String connect(ClientInt client) throws RemoteException {
        return null;
    }


    @Override
    public String disconnect(ClientInt client) throws RemoteException {
        return null;
    }

    @Override
    public List<FriendEntity> getAllFriends(String mobile) throws RemoteException {
        UserFriendDao userFriendDao = new UserFriendDao();
        return userFriendDao.getFriendList(mobile);
    }

    @Override
    public List<FriendEntity> getAllFriendsRequest(String mobile) throws RemoteException {
        UserFriendDao userFriendDao = new UserFriendDao();
        return userFriendDao.getFriendRequests(mobile);
    }

    @Override
    public GroupEntity createGroup(GroupEntity entity) throws RemoteException {
        GroupDao groupDao = new GroupDao();
        Group group = new Group(entity.getName(), entity.getDescription(), entity.getOwner_mobile());
        groupDao.save(group);
        return entity;
    }

    @Override
    public List<GroupEntity> getUsersGroup(int userId) throws RemoteException {
        GroupDao groupDao = new GroupDao();
        List<GroupEntity> groupList = groupDao.getUsersGroup(userId);
        return groupList;
    }

    @Override
    public void addGroupMembers(List<GroupMember> members) throws RemoteException {
        GroupMemberDao groupMemberDao = new GroupMemberDao();
        members.forEach(member->{
            groupMemberDao.save(member);
        });
    }
}
