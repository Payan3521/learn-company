package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import com.desarrollox.learncompany.domain.model.LoginAttempt;
import com.desarrollox.learncompany.domain.model.RefreshToken;
import com.desarrollox.learncompany.persistence.entity.LoginAttemptEntity;
import com.desarrollox.learncompany.persistence.entity.RefreshTokenEntity;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthenticationMapper {
    RefreshTokenEntity toEntity(RefreshToken model);
    RefreshToken toModel(RefreshTokenEntity entity);
    LoginAttemptEntity toEntity(LoginAttempt model);
    LoginAttempt toModel(LoginAttemptEntity entity);
}