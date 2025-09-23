package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.NotificationContent;
import com.desarrollox.learncompany.persistence.entity.NotificationContentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {NotificationMapper.class})
public interface NotificationContentMapper {

    NotificationContentEntity toEntity(NotificationContent notificationContent);

    NotificationContent toDomain(NotificationContentEntity notificationContentEntity);
}