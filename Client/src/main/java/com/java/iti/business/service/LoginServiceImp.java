package com.java.iti.business.service;

import com.java.iti.business.dtos.UserDto;
import com.java.iti.exception.CredentialException;
import com.java.iti.model.user.User;

public class LoginServiceImp implements LoginService {


    @Override
    public boolean logInto(UserDto dto) throws CredentialException {
        User currentUser = null;
        if (currentUser == null) {
            throw new CredentialException("Phone Or Password May Be Invalid");
        }
        if(dto.getStatus().equals("AVAILABLE")) {
            throw new CredentialException("User is already logged in");
        }
        if(!currentUser.getPassword().equals(dto.getPassword())) {
            throw new CredentialException("Invalid password");
        }
        return true;
    }

    @Override
    public int logOut(UserDto dto) throws CredentialException {
        return 0;
    }
}
