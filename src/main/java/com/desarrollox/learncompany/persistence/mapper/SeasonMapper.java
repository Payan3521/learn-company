package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.persistence.entity.SeasonEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CourseMapper.class})
public interface SeasonMapper {

    @Mapping(source = "duration", target = "durationInHours")
    SeasonEntity toEntity(Season season);

    @Mapping(source = "durationInHours", target = "duration")
    Season toDomain(SeasonEntity seasonEntity);
}