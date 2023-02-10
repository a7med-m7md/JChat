package Server.network.interfaces;

import Models.*;
import Server.business.model.user.User;
import exceptions.UserNotFoundException;
import Server.persistance.dao.UserDao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
}
