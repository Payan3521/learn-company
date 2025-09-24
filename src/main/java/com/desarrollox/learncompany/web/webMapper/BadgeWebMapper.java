package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.web.dto.BadgeRequest;
import com.desarrollox.learncompany.web.dto.BadgeResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BadgeWebMapper {
    
    @Mapping(target = "id", ignore = true)
    Badge requestToDomain(BadgeRequest request);
    
    BadgeResponse domainToResponse(Badge domain);
}