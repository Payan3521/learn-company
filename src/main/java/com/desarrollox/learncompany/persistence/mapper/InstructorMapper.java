package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.persistence.entity.InstructorEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {DepartmentMapper.class, CourseMapper.class})
public interface InstructorMapper {
    
    Instructor toDomain(InstructorEntity entity);
    InstructorEntity toEntity(Instructor domain);
}