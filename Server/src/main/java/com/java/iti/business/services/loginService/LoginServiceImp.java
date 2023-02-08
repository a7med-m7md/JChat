package com.java.iti.business.services.loginService;

import com.java.iti.business.dtos.UserDto;
import com.java.iti.exception.CredentialException;
import com.java.iti.network.interfaces.LoginEntity;
import com.java.iti.persistance.entities.UserEntity;
import com.java.iti.persistance.dao.UserDao;

import java.util.Optional;


public class LoginServiceImp implements LoginService {
    UserDao userDao = new UserDao();

    @Override
    public boolean logInto(LoginEntity userDto) throws CredentialException {
        if (!userDto.getPassword().equals(userDto.getPassword()) &&
                !userDto.getMobile().equals(userDto.getMobile())) {
            throw new CredentialException("Invalid password");
        }
        userDao.userLogin(userDto);
        return true;
    }

    @Override
    public void logOut(int id) {
        Optional<UserEntity> currentUser = userDao.findById(1);
        if (currentUser == null) {
            throw new CredentialException("Phone Or Password May Be Invalid");
        }
        userDao.delete(id);
    }
}
