package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DepartmentMapper.class})
public interface UserMapper {

    UserEntity toUserEntity(User user);

    User toUser(UserEntity userEntity);
}