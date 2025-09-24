package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.web.dto.InscriptionRequest;
import com.desarrollox.learncompany.web.dto.InscriptionResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface InscriptionWebMapper {
    
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
    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "course.modules", ignore = true)
    @Mapping(target = "course.inscriptions", ignore = true)
    @Mapping(target = "course.title", ignore = true)
    @Mapping(target = "course.topic", ignore = true)
    @Mapping(target = "course.description", ignore = true)
    @Mapping(target = "course.level", ignore = true)
    @Mapping(target = "course.duration", ignore = true)
    @Mapping(target = "course.season", ignore = true)
    @Mapping(target = "course.instructor", ignore = true)
    @Mapping(target = "dateIssued", ignore = true)
    @Mapping(target = "status", constant = "IN_PROGRESS")
    Inscription requestToDomain(InscriptionRequest request);
    
    @Mapping(target = "dateAndHour", source = "dateIssued")
    @Mapping(target = "employeeId", source = "employee.id")
    @Mapping(target = "courseId", source = "course.id")
    InscriptionResponse domainToResponse(Inscription domain);
}