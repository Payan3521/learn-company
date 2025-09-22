package com.desarrollox.learncompany.persistence.repository.mapper;

import org.mapstruct.Mapper;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.persistence.entity.DepartmentEntity;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
    DepartmentEntity toDepartmentEntity(Department department);
    Department toDepartment(DepartmentEntity entity);
}