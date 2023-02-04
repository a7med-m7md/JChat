package Server.business.services.register;

import Server.business.dtos.UserDto;

public interface RegisterService {
    boolean isNewUser(int id);

    UserDto register(UserDto userDto);
}
