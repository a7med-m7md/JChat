package com.java.iti.business.service;

import com.java.iti.business.dtos.UserDto;

public interface LoginService {
    public boolean logInto(UserDto dto);
    public int logOut(UserDto dto);
}
