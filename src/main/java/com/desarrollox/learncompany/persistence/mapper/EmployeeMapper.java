package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.persistence.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CertificateMapper.class, InscriptionMapper.class})
public interface EmployeeMapper {


    EmployeeEntity toEmployeeEntity(Employee employee);


    Employee toEmployee(EmployeeEntity employeeEntity);
}