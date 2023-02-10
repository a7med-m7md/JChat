package Server.business.services.register;

import Server.business.mappers.UseMapperImpl;
import Server.business.mappers.UserMapper;
import Models.UserEntity;
import Server.persistance.dao.UserDao;
import Server.business.dtos.UserDto;


import java.util.Optional;

public class RegisterServiceImpl implements RegisterService{
    //TODO -> should connect with server using RMI to access the register service there to connect to db
    //TODO -> BUT Now we will check with local db.
    UserDao userDao = new UserDao();
    UserMapper userMapper = new UseMapperImpl();
    @Override
    public boolean isNewUser(int id) {
        Optional<UserEntity> optionalEntity = userDao.findById(id);
        if (optionalEntity.isEmpty())
            return true;
        else
            return false;
    }

    @Override
    public Server.business.dtos.UserDto register(UserDto userDto) {
            UserEntity userEntity = userMapper.domainToEntity(userDto);
            userDao.save(userEntity);
            return userDto;
    }
}
