package Server.business.mappers;

import Server.persistance.entities.UserEntity;
import Server.business.dtos.UserDto;

public class UseMapperImpl implements UserMapper{
    @Override
    public Server.business.dtos.UserDto entityToDomain(UserEntity entity) {
        Server.business.dtos.UserDto userDto = new Server.business.dtos.UserDto(
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
