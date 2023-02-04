package com.java.iti.business.services.loginService;

import com.java.iti.business.dtos.UserDto;
import com.java.iti.network.interfaces.LoginEntity;

public interface LoginService {
    public boolean logInto(LoginEntity dto);

    public void logOut(int id);
}
