package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.persistence.entity.SeasonEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonMapper {
    
    @Mapping(source = "durationInHours", target = "duration")
    Season toDomain(SeasonEntity entity);
    
    @Mapping(source = "duration", target = "durationInHours")
    SeasonEntity toEntity(Season domain);
}