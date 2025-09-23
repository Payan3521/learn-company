package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.web.dto.BadgeRequest;
import com.desarrollox.learncompany.web.dto.BadgeResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {UserWebMapper.class})
public interface BadgeWebMapper {
    
    @Mapping(target = "id", ignore = true)
    Badge requestToDomain(BadgeRequest request);
    
    @Mapping(target = "employee", ignore = true) // Se maneja por separado
    BadgeResponse domainToResponse(Badge domain);
}