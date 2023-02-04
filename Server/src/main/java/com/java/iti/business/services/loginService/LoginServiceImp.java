package com.java.iti.business.services.loginService;
import com.java.iti.business.dtos.UserDto;
import com.java.iti.exception.CredentialException;
import com.java.iti.persistance.entities.UserEntity;
import com.java.iti.persistance.dao.UserDao;

import java.util.Optional;


public class LoginServiceImp implements LoginService {
    UserDao userDao = new UserDao();
    @Override
    public boolean logInto(UserDto userDto) throws CredentialException {
        Optional<UserEntity> currentUser = userDao.findById((int) userDto.getId());
        if (currentUser == null) {
            throw new CredentialException("Phone Or Password May Be Invalid");
        }
        if(userDto.getStatus().equals("AVAILABLE")) {
            throw new CredentialException("User is already logged in");
        }
        if(!currentUser.get().getPassword().equals(userDto.getPassword())) {
            throw new CredentialException("Invalid password");
        }
        return true;
    }

    @Override
    public void logOut(int id) {
        userDao.delete(id);
    }
}
