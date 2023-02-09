package Server.business.services.login;

import Models.LoginEntity;

public interface LoginService {
    public boolean logInto(LoginEntity dto);

    public void logOut(int id);
}