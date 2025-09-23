package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.persistence.entity.DepartmentEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
    
    Department toDomain(DepartmentEntity entity);
    DepartmentEntity toEntity(Department domain);
}