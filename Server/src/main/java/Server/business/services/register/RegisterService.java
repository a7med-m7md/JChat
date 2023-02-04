package Server.business.services.register;

import Server.business.dtos.UserDto;

public interface RegisterService {
    boolean isNewUser(int id);

    Server.business.dtos.UserDto register(UserDto userDto);
}
