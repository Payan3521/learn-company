package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.web.dto.EmployeeRequest;
import com.desarrollox.learncompany.web.dto.UserResponse;

@Component
@Mapper(
    componentModel = "spring",
    uses = { BadgeWebMapper.class, CertificateWebMapper.class, InscriptionWebMapper.class }
)
public interface UserWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "puntos", constant = "0")
    @Mapping(target = "badges", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "certificates", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "inscriptions", expression = "java(new java.util.ArrayList<>())")
    Employee toEmployee(EmployeeRequest request);

    UserResponse toUserResponse(Employee employee);
}