package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.persistence.entity.EmployeeEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {DepartmentMapper.class})
public interface EmployeeMapper {
    
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    Employee toDomain(EmployeeEntity entity);

    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    EmployeeEntity toEntity(Employee domain);
}