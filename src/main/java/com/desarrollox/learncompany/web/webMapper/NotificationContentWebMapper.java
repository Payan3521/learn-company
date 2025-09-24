package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.NotificationContent;
import com.desarrollox.learncompany.web.dto.NotificationContentRequest;
import com.desarrollox.learncompany.web.dto.NotificationContentResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationContentWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "notification", ignore = true)
    NotificationContent requestToDomain(NotificationContentRequest request);
    
    @Mapping(target = "notificationId", source = "notification.id")
    NotificationContentResponse domainToResponse(NotificationContent domain);
}