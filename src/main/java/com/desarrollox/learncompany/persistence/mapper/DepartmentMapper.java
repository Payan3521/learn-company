package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.persistence.entity.DepartmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    DepartmentEntity toEntity(Department department);

    Department toDomain(DepartmentEntity departmentEntity);
}