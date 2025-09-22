package com.desarrollox.learncompany.persistence.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.persistence.entity.UserEntity;

@Mapper(
    componentModel = "spring",
    uses = {
        DepartmentMapper.class,
        BadgeMapper.class,
        CertificateMapper.class,
        InscriptionMapper.class
    },
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {
    UserEntity toUserEntity(User user);
    User toUser(UserEntity entity);
}