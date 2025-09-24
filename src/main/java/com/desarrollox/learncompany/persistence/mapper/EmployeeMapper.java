package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.persistence.entity.EmployeeEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {DepartmentMapper.class, CertificateMapper.class, InscriptionMapper.class})
public interface EmployeeMapper {
    
    Employee toDomain(EmployeeEntity entity);

    @Mapping(target = "id", ignore = true)
    EmployeeEntity toEntity(Employee domain);
}