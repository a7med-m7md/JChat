package com.java.iti.business.services.loginService;

import com.java.iti.business.dtos.UserDto;

public interface LoginService {
    public boolean logInto(UserDto dto);

    public void logOut(int id);
}
