package Server.network.services;

import Server.business.mappers.GroupMapper;
import Server.business.mappers.GroupMapperImp;
import Server.business.model.group.Group;
import Server.persistance.dao.GroupDao;
import model.*;
import exceptions.UserNotFoundException;
import Server.persistance.dao.UserDao;
import model.group.GroupEntity;
import model.user.UserEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
