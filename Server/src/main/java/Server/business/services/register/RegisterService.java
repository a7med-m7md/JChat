package Server.business.services.register;

import Server.business.dtos.UserDto;

public interface RegisterService {
    boolean isNewUser(int id);
    boolean isNewUser(String phone);


    Server.business.dtos.UserDto register(UserDto userDto);
}
