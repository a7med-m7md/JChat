package com.java.iti.network.interfaces;

import com.java.iti.business.model.user.User;
import com.java.iti.persistance.dao.UserDao;
import com.java.iti.persistance.entities.UserEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

public class CheckLogin extends UnicastRemoteObject implements Remote, ServerInt {
    public CheckLogin() throws RemoteException {
    }

    @Override
    public String login(LoginEntity userInfo) throws RemoteException {
        UserDao user = new UserDao();
        System.out.println(user.userLogin(userInfo));
        System.out.println("Logged in successfully");

        return null;
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
