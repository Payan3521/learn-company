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
        uses = {DepartmentWebMapper.class, BadgeWebMapper.class, 
                CertificateWebMapper.class, InscriptionWebMapper.class, 
                CourseWebMapper.class})
public interface UserWebMapper {
    
    @Mapping(target = "puntos", source = "puntos")
    @Mapping(target = "badges", ignore = true) // Se mapea por separado en el servicio
    @Mapping(target = "certificates", source = "certificates")
    @Mapping(target = "inscriptions", source = "inscriptions")
    @Mapping(target = "especialidad", ignore = true)
    @Mapping(target = "biografia", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "age", ignore = true)
    UserResponse employeeToResponse(Employee domain);
    
    @Mapping(target = "puntos", ignore = true)
    @Mapping(target = "badges", ignore = true)
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(target = "especialidad", source = "specialty")
    @Mapping(target = "biografia", source = "biography")
    @Mapping(target = "courses", source = "courses")
    @Mapping(target = "age", ignore = true)
    UserResponse instructorToResponse(Instructor domain);
    
    @Mapping(target = "puntos", ignore = true)
    @Mapping(target = "badges", ignore = true)
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(target = "especialidad", ignore = true)
    @Mapping(target = "biografia", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "age", source = "age")
    UserResponse administratorToResponse(Administrator domain);
    
    @Mapping(target = "puntos", ignore = true)
    @Mapping(target = "badges", ignore = true)
    @Mapping(target = "certificates", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(target = "especialidad", ignore = true)
    @Mapping(target = "biografia", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "age", ignore = true)
    UserResponse userToResponse(User domain);
}