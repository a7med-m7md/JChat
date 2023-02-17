package Server.business.services.register;

import Server.business.mappers.UseMapperImpl;
import Server.business.mappers.UserMapper;
import model.user.UserEntity;
import Server.persistance.dao.UserDao;
import model.user.UserDto;
import services.RegisterService;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

public class RegisterServiceImpl extends UnicastRemoteObject implements RegisterService {
    UserDao userDao = new UserDao();
    UserMapper userMapper = new UseMapperImpl();

    public RegisterServiceImpl() throws RemoteException {
    }

    @Override
    public boolean isNewUser(int id) {
        Optional<UserEntity> optionalEntity = userDao.findById(id);
        if (optionalEntity.isEmpty())
            return true;
        else
            return false;
    }

    @Override
    public UserEntity register(UserDto userDto) {
        UserEntity userEntity = userMapper.domainToEntity(userDto);
        userDao.save(userEntity);
        return userEntity;
    }

    @Override
    public boolean isNewUser(String phone) {

        Optional<UserEntity> optionalEntity = userDao.findByMobile(phone);
        if (optionalEntity.isEmpty())
            return true;
        else
            return false;
    }
}
