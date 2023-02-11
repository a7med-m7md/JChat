package Server.business.mappers;

import model.user.UserEntity;
import model.user.UserDto;

public interface UserMapper {
    UserDto entityToDomain(UserEntity entity);
    UserEntity domainToEntity(UserDto userDto);

}
