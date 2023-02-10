package Server.business.mappers;

import model.user.UserEntity;
import Server.business.dtos.UserDto;

public interface UserMapper {
    Server.business.dtos.UserDto entityToDomain(UserEntity entity);
    UserEntity domainToEntity(UserDto userDto);

}
