package com.java.iti.business.mappers;

import com.java.iti.business.dtos.UserDto;
import com.java.iti.persistance.entities.UserEntity;

public interface UserMapper {
    UserDto entityToDomain(UserEntity entity);
    UserEntity domainToEntity(UserDto userDto);

}
