package Server.business.services.login;

import model.LoginEntity;

import javax.security.auth.login.CredentialException;

public interface LoginService {
    public boolean logInto(LoginEntity dto) throws CredentialException;

    public void logOut(int id) throws CredentialException;
}