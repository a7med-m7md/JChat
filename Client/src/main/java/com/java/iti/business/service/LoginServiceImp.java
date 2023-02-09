package com.java.iti.business.service;

import Client.business.dtos.UserDto;
import Client.model.user.User;
import com.java.iti.exception.CredentialException;

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
