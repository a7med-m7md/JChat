package Server.network.services;

import Server.business.mappers.GroupMapper;
import Server.business.mappers.GroupMapperImp;
import Server.business.mappers.UseMapperImpl;
import Server.business.mappers.UserMapper;
import Server.business.model.group.Group;
import Server.business.services.ConnectedService;
import Server.business.services.register.RegisterServiceImpl;
import Server.persistance.dao.GroupDao;
import exceptions.DuplicateUserException;
import model.*;
import exceptions.UserNotFoundException;
import Server.persistance.dao.UserDao;
import model.group.GroupEntity;
import model.user.UserEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Optional;

public class RMIServerServices extends UnicastRemoteObject implements Remote, ServerInt {
    ConnectedService connectedService = new ConnectedService();

    public RMIServerServices() throws RemoteException {
    }

    @Override
    public UserEntity login(LoginEntity userInfo) throws UserNotFoundException {
        UserDao user = new UserDao();
        Optional<UserEntity> userEntity = user.userLogin(userInfo);
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
    public void signUp(UserEntity userEntity) throws RemoteException {
        UserMapper userMapper = new UseMapperImpl();
        RegisterServiceImpl registerService = new RegisterServiceImpl();
        registerService.register(userMapper.entityToDomain(userEntity));
    }


    @Override
    public String logout(String name) throws RemoteException {
        return null;
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
    public GroupEntity createGroup(GroupEntity entity) throws RemoteException {
        GroupDao groupDao = new GroupDao();
        GroupMapper groupMapper = new GroupMapperImp();
        Group group = new Group(entity.getName(), entity.getDescription(), entity.getOwner_id());
        groupDao.save(group);
        return entity;
    }

    @Override
    public List<GroupEntity> getUserGroups(int userId) throws RemoteException {
        GroupDao groupDao = new GroupDao();
        GroupMapper groupMapper = new GroupMapperImp();
        List<GroupEntity> groupList = groupDao.getUserGroups(userId);
        return groupList;
    }
}
