package Server.network.services;

import Server.business.mappers.GroupMapper;
import Server.business.mappers.GroupMapperImp;
import Server.business.mappers.UseMapperImpl;
import Server.business.mappers.UserMapper;
import Server.business.model.group.Group;
import Server.business.services.register.RegisterServiceImpl;
import Server.persistance.dao.GroupDao;
import exceptions.DuplicationUserException;
import model.*;
import exceptions.UserNotFoundException;
import Server.persistance.dao.UserDao;
import model.group.GroupEntity;
import model.user.UserEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.List;
import java.util.Optional;

public class CheckLogin extends UnicastRemoteObject implements Remote, ServerInt {
    public CheckLogin() throws RemoteException {
    }

    @Override
    public UserEntity login(LoginEntity userInfo) throws UserNotFoundException {
        UserDao user = new UserDao();
        System.out.println(userInfo.getMobile());
        System.out.println(userInfo.getPassword());
        Optional<UserEntity> userEntity = user.userLogin(userInfo);
        if (userEntity.isPresent()) {
            System.out.println("Logged in successfully");
            return userEntity.orElse(null);
        } else {
            System.out.println("Can't login");
            throw new UserNotFoundException();
        }
    }

    public void getDuplicated(String phoneNumber) throws RemoteException, DuplicationUserException {
        UserMapper userMapper = new UseMapperImpl();
        RegisterServiceImpl registerService = new RegisterServiceImpl();
        if (!registerService.isNewUser(phoneNumber)) {
            throw new DuplicationUserException();
        }
    }

    @Override
    public void signUp(UserEntity userEntity) throws RemoteException, DuplicateFormatFlagsException {
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
