package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.persistence.entity.CourseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SeasonMapper.class, InstructorMapper.class, ModuleMapper.class, InscriptionMapper.class})
public interface CourseMapper {

    @Mapping(source = "duration", target = "durationInHours")
    CourseEntity toEntity(Course course);

    @Mapping(source = "durationInHours", target = "duration")
    Course toDomain(CourseEntity courseEntity);
}