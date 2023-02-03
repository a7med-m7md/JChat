package com.java.iti.business.services.register;

import com.java.iti.business.dtos.UserDto;
import com.java.iti.repository.entities.UserEntity;

public interface RegisterService {
    boolean isNewUser(int id);

    UserDto register(UserDto userDto);
}
