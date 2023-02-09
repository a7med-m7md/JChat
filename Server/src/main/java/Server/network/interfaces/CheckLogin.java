package Server.network.interfaces;

import Models.ClientInt;
import Models.LoginEntity;
import Models.ServerInt;
import exceptions.UserNotFoundException;
import Server.persistance.dao.UserDao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CheckLogin extends UnicastRemoteObject implements Remote, ServerInt {
    public CheckLogin() throws RemoteException {
    }

    @Override
    public void login(LoginEntity userInfo) throws UserNotFoundException {
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
