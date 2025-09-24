package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.persistence.entity.InstructorEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {DepartmentMapper.class})
public interface InstructorMapper {
    
    @Mapping(target = "courses", ignore = true)
    Instructor toDomain(InstructorEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    InstructorEntity toEntity(Instructor domain);
}