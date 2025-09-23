package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.persistence.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {DepartmentMapper.class})
public interface UserMapper {
    
    @Mapping(source = "role", target = "role")
    User toDomain(UserEntity entity);
    
    @Mapping(source = "role", target = "role")
    UserEntity toEntity(User domain);
}