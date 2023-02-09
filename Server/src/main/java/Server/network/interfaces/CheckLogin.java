package Server.network.interfaces;

import Models.ClientInt;
import Models.LoginEntity;
import Models.ServerInt;
import Models.UserEntity;
import Server.business.mappers.UseMapperImpl;
import Server.business.mappers.UserMapper;
import Server.business.services.register.RegisterServiceImpl;
import exceptions.UserDuplicatedException;
import exceptions.UserNotFoundException;
import Server.persistance.dao.UserDao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CheckLogin extends UnicastRemoteObject implements Remote, ServerInt {
    public CheckLogin() throws RemoteException {
    }

    @Override
    public void login(LoginEntity userInfo) throws RemoteException {
        UserDao user = new UserDao();
        System.out.println(userInfo.getMobile());
        System.out.println(userInfo.getPassword());
        if (user.userLogin(userInfo).isPresent()) {
            System.out.println("Logged in successfully");
        } else {
            System.out.println("Can't login");
            throw new UserNotFoundException();
        }
    }

    public void getDuplicated(String phoneNumber) throws RemoteException, UserDuplicatedException {
        UserMapper userMapper = new UseMapperImpl();
        RegisterServiceImpl registerService = new RegisterServiceImpl();
        if (!registerService.isNewUser(phoneNumber)) {
            throw new UserDuplicatedException();
        }
    }

    @Override
    public void signIn(Models.UserEntity user) throws RemoteException {
        UserMapper userMapper = new UseMapperImpl();
        RegisterServiceImpl registerService = new RegisterServiceImpl();
        if (registerService.isNewUser((int) (user.getId()))) {
            registerService.register(userMapper.entityToDomain(user));
        } else {
            System.out.println("Can't Register");
            //throw new UserNotFoundException();
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
}
