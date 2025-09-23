package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.web.dto.AssessmentInstanceRequest;
import com.desarrollox.learncompany.web.dto.AssessmentInstanceResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AssessmentTemplateWebMapper.class, UserWebMapper.class, AnswerWebMapper.class})
public interface AssessmentInstanceWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assessmentTemplate.id", source = "assessmentTemplateId")
    @Mapping(target = "assessmentTemplate.module", ignore = true)
    @Mapping(target = "assessmentTemplate.questions", ignore = true)
    @Mapping(target = "assessmentTemplate.type", ignore = true)
    @Mapping(target = "assessmentTemplate.retries", ignore = true)
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
    @Mapping(target = "grade", constant = "0.0")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "answers", source = "answers")
    @Mapping(target = "createdAt", ignore = true)
    AssessmentInstance requestToDomain(AssessmentInstanceRequest request);
    
    AssessmentInstanceResponse domainToResponse(AssessmentInstance domain);
}