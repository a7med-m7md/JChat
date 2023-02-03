package com.java.iti.client.business.mappers;

import com.java.iti.client.business.dtos.UserDto;
import com.java.iti.client.repository.entities.UserEntity;

public interface UserMapper {
    UserDto entityToDomain(UserEntity entity);
    UserEntity domainToEntity(UserDto userDto);

}
