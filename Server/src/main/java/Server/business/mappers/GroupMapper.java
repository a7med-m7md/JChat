package Server.business.mappers;

import Server.business.dtos.GroupDto;
import model.group.GroupEntity;

public interface GroupMapper {
    GroupDto entityToDomain(GroupEntity entity);
    GroupEntity domainToEntity(GroupEntity entity);
}
