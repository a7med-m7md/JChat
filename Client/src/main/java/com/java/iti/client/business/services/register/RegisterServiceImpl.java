package com.java.iti.client.business.services.register;

import com.java.iti.client.business.dtos.UserDto;
import com.java.iti.client.business.mappers.UseMapperImpl;
import com.java.iti.client.business.mappers.UserMapper;
import com.java.iti.client.repository.entities.UserEntity;
import com.java.iti.client.repository.userDao.UserDao;

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
    public UserDto register(UserDto userDto) {
            UserEntity userEntity = userMapper.domainToEntity(userDto);
            userDao.save(userEntity);
            return userDto;
    }
}