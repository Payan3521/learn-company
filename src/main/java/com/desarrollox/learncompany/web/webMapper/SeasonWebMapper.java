package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.web.dto.SeasonRequest;
import com.desarrollox.learncompany.web.dto.SeasonResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CourseWebMapper.class})
public interface SeasonWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Season requestToDomain(SeasonRequest request);
    
    SeasonResponse domainToResponse(Season domain);
}