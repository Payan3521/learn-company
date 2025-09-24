package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.persistence.entity.DepartmentEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
    
    Department toDomain(DepartmentEntity entity);

    @Mapping(target = "id", ignore = true)
    DepartmentEntity toEntity(Department domain);
}