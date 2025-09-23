package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.Notification;
import com.desarrollox.learncompany.persistence.entity.NotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, NotificationContentMapper.class})
public interface NotificationMapper {

    NotificationEntity toEntity(Notification notification);

    Notification toDomain(NotificationEntity notificationEntity);
}