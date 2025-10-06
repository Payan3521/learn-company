package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.persistence.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {DepartmentMapper.class})
public interface UserMapper {

    User toDomain(UserEntity entity);
    
    UserEntity toEntity(User domain);
    
}