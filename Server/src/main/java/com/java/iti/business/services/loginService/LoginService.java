package com.java.iti.business.services.loginService;

import Models.LoginEntity;

public interface LoginService {
    public boolean logInto(LoginEntity dto);

    public void logOut(int id);
}
