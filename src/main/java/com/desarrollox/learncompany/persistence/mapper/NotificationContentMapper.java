package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.NotificationContent;
import com.desarrollox.learncompany.persistence.entity.NotificationContentEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationContentMapper {
    
    NotificationContent toDomain(NotificationContentEntity entity);
    NotificationContentEntity toEntity(NotificationContent domain);
}