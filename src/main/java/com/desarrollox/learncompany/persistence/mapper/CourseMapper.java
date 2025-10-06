package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.persistence.entity.CourseEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {SeasonMapper.class, InstructorMapper.class})
public interface CourseMapper {
    
    @Mapping(target = "duration", source = "durationInHours" )
    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    Course toDomain(CourseEntity entity);
    
    @Mapping(target = "durationInHours", source = "duration")
    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    CourseEntity toEntity(Course domain);
}