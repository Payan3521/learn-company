package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Notification;
import com.desarrollox.learncompany.web.dto.NotificationRequest;
import com.desarrollox.learncompany.web.dto.NotificationResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {NotificationContentWebMapper.class})
public interface NotificationWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "user.email", ignore = true)
    @Mapping(target = "user.password", ignore = true)
    @Mapping(target = "user.name", ignore = true)
    @Mapping(target = "user.lastname", ignore = true)
    @Mapping(target = "user.status", ignore = true)
    @Mapping(target = "user.role", ignore = true)
    @Mapping(target = "user.department", ignore = true)
    @Mapping(target = "user.urlPhoto", ignore = true)
    @Mapping(target = "dateIssued", ignore = true)
    @Mapping(target = "readStatus", constant = "false")
    Notification requestToDomain(NotificationRequest request);
    
    @Mapping(target = "userId", source = "user.id")
    NotificationResponse domainToResponse(Notification domain);
}