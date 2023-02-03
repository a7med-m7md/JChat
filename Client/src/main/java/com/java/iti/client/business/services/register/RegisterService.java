package com.java.iti.client.business.services.register;

import com.java.iti.client.business.dtos.UserDto;
import com.java.iti.client.repository.entities.UserEntity;

public interface RegisterService {
    boolean isNewUser(int id);

    UserDto register(UserDto userDto);
}
