package Server.business.mappers;

import Server.business.dtos.GroupDto;
import Server.business.dtos.UserDto;
import Server.persistance.dao.GroupDao;
import model.group.GroupEntity;
import model.user.UserEntity;

public class GroupMapperImp implements GroupMapper {


    @Override
    public GroupDto entityToDomain(GroupEntity entity) {
        GroupDto groupDao = new GroupDto(
                entity.getName(),
                entity.getDescription(),
                entity.getOwner_id());
        return groupDao;
    }

    @Override
    public GroupEntity domainToEntity(GroupEntity entity) {
        GroupEntity groupEntity = new GroupEntity(
                entity.getName(),
                entity.getDescription(),
                entity.getOwner_id()
        );
        return groupEntity;
    }
}
