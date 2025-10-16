package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.web.dto.EmployeeRequest;
import com.desarrollox.learncompany.web.dto.EmployeeUpdateRequest;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "true")
    @Mapping(target = "department.id", source = "departmentId")
    @Mapping(target = "department.name", ignore = true)
    @Mapping(target = "department.prize", ignore = true)
    @Mapping(target = "department.hierarchy", ignore = true)
    @Mapping(target = "puntos", constant = "0")
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    Employee requestToDomain(EmployeeRequest request);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "department.id", source = "departmentId")
    @Mapping(target = "department.name", ignore = true)
    @Mapping(target = "department.prize", ignore = true)
    @Mapping(target = "department.hierarchy", ignore = true)
    @Mapping(target = "puntos", constant = "0")
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    Employee updateRequestToDomain(EmployeeUpdateRequest request);
}