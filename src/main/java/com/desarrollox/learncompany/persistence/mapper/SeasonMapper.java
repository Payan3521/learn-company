package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.persistence.entity.SeasonEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonMapper {
    
    @Mapping(target = "duration", source = "durationInHours" )
    @Mapping(target = "courses", ignore = true)
    Season toDomain(SeasonEntity entity);
    
    @Mapping(target = "durationInHours", source = "duration" )
    @Mapping(target = "courses", ignore = true)
    SeasonEntity toEntity(Season domain);
}