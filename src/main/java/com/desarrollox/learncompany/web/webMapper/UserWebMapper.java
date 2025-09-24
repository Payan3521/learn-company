package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Administrator;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.web.dto.UserResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {DepartmentWebMapper.class, CertificateWebMapper.class, InscriptionWebMapper.class, 
                CourseWebMapper.class})
public interface UserWebMapper {
    
    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "specialty", ignore = true)
    @Mapping(target = "biography", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "age", ignore = true)
    UserResponse employeeToResponse(Employee domain);
    
    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "puntos", ignore = true)
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(target = "age", ignore = true)
    UserResponse instructorToResponse(Instructor domain);
    
    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "puntos", ignore = true)
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(target = "specialty", ignore = true)
    @Mapping(target = "biography", ignore = true)
    @Mapping(target = "courses", ignore = true)
    UserResponse administratorToResponse(Administrator domain);
    
    @Mapping(target = "departmentId", source = "department.id")
    @Mapping(target = "puntos", ignore = true)
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(target = "specialty", ignore = true)
    @Mapping(target = "biography", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "age", ignore = true)
    UserResponse userToResponse(User domain);
}