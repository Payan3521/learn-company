package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.persistence.entity.CourseEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ModuleMapper.class, InscriptionMapper.class, SeasonMapper.class, InstructorMapper.class})
public interface CourseMapper {
    
    @Mapping(source = "durationInHours", target = "duration")
    Course toDomain(CourseEntity entity);
    
    @Mapping(source = "duration", target = "durationInHours")
    CourseEntity toEntity(Course domain);
}