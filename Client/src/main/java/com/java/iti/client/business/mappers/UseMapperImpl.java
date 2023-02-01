package com.java.iti.client.business.mappers;

import com.java.iti.client.business.dtos.UserDto;
import com.java.iti.client.repository.entities.UserEntity;

public class UseMapperImpl implements UserMapper{
    @Override
    public UserDto entityToDomain(UserEntity entity) {
        UserDto userDto = new UserDto(
                entity.getMobile(),
                entity.getName(),
                entity.getEmail(),
                entity.getPicture(),
                entity.getPassword(),
                entity.getGender(),
                entity.getCountry(),
                entity.getDateOfBirth(),
                entity.getBio(),
                entity.getStatus()
        );
        return userDto;
    }

    @Override
    public UserEntity domainToEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity(
                userDto.getMobile(),
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPicture(),
                userDto.getPassword(),
                userDto.getGender(),
                userDto.getCountry(),
                userDto.getDateOfBirth(),
                userDto.getBio(),
                userDto.getStatus()
        );
        return userEntity;
    }
}
