package Server.business.services.login;

import model.LoginEntity;
import model.user.UserStatus;

import javax.security.auth.login.CredentialException;

public interface LoginService {
    public boolean logInto(LoginEntity dto) throws CredentialException;

    public void logOut(String mobile, UserStatus status) throws CredentialException;
}