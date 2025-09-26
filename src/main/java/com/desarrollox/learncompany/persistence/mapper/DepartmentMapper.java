package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.persistence.entity.DepartmentEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
    
    @Mapping(target = "courses", ignore = true)
    Department toDomain(DepartmentEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    DepartmentEntity toEntity(Department domain);
}