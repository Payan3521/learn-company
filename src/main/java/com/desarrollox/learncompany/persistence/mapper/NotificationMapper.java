package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Notification;
import com.desarrollox.learncompany.persistence.entity.NotificationEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserMapper.class, NotificationContentMapper.class})
public interface NotificationMapper {
    
    Notification toDomain(NotificationEntity entity);

    @Mapping(target = "id", ignore = true)
    NotificationEntity toEntity(Notification domain);
}