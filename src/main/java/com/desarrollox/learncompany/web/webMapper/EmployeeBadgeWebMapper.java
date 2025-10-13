package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.EmployeeBadge;
import com.desarrollox.learncompany.web.dto.AssignBadgeRequest;
import com.desarrollox.learncompany.web.dto.EmployeeBadgeResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
    uses = {UserWebMapper.class, BadgeWebMapper.class})
public interface EmployeeBadgeWebMapper {

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
    @Mapping(target = "badge.id", source = "badgeId")
    @Mapping(target = "badge.name", ignore = true)
    @Mapping(target = "badge.urlIcon", ignore = true)
    @Mapping(target = "badge.criteria", ignore = true)
    @Mapping(target = "dateEarned", ignore = true) 
    EmployeeBadge requestToDomain(AssignBadgeRequest request);

    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "badgeId", source = "badge.id")
    EmployeeBadgeResponse domainToResponse(EmployeeBadge domain);
}