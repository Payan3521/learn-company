package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.web.dto.BadgeRequest;
import com.desarrollox.learncompany.web.dto.BadgeResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BadgeWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "employee.id", source = "employeeId")
    @Mapping(target = "employee.email", ignore = true)
    @Mapping(target = "employee.password", ignore = true)
    @Mapping(target = "employee.name", ignore = true)
    @Mapping(target = "employee.lastname", ignore = true)
    @Mapping(target = "employee.status", ignore = true)
    @Mapping(target = "employee.role", ignore = true)
    @Mapping(target = "employee.department", ignore = true)
    @Mapping(target = "employee.urlPhoto", ignore = true)
    @Mapping(target = "employee.puntos", ignore = true)
    @Mapping(target = "employee.certificates", ignore = true)
    @Mapping(target = "employee.inscriptions", ignore = true)
    Badge requestToDomain(BadgeRequest request);
    
    @Mapping(target = "employeeId", source = "employee.id")
    BadgeResponse domainToResponse(Badge domain);
}