package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.web.dto.InstructorRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InstructorWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "department.id", source = "departmentId")
    @Mapping(target = "department.name", ignore = true)
    @Mapping(target = "department.prize", ignore = true)
    @Mapping(target = "department.hierarchy", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Instructor requestToDomain(InstructorRequest request);
}