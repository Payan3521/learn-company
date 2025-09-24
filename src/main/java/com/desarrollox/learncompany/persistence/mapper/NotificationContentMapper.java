package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.NotificationContent;
import com.desarrollox.learncompany.persistence.entity.NotificationContentEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, 
    uses = {NotificationMapper.class})
public interface NotificationContentMapper {
    
    NotificationContent toDomain(NotificationContentEntity entity);

    @Mapping(target = "id", ignore = true)
    NotificationContentEntity toEntity(NotificationContent domain);
}