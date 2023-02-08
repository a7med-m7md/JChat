package Server.business.services.login;

import Models.LoginEntity;
import Server.persistance.dao.UserDao;
import Server.persistance.entities.UserEntity;
import com.java.iti.exception.CredentialException;

import java.util.Optional;


public class LoginServiceImp implements LoginService {
    UserDao userDao = new UserDao();

    @Override
    public boolean logInto(LoginEntity userDto) throws CredentialException {
        Optional<UserEntity> currentUser = userDao.findById(1);
        if (currentUser == null) {
            throw new CredentialException("Phone Or Password May Be Invalid");
        }
        if (!currentUser.get().getPassword().equals(userDto.getPassword()) &&
                !currentUser.get().getMobile().equals(userDto.getMobile())) {
            throw new CredentialException("Invalid password");
        }
        userDao.userLogin(userDto);
        return true;
    }

    @Override
    public void logOut(int id) {
        Optional<UserEntity> currentUser = userDao.findById(1);
        if (currentUser == null) {
            throw new CredentialException("Phone Or Password May Be Invalid");
        }
        userDao.delete(id);

    }
}