package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.persistence.entity.AssessmentInstanceEntity;

@Mapper(componentModel = "spring", uses = {AssessmentTemplateMapper.class, EmployeeMapper.class, AnswerMapper.class})
public interface AssessmentInstanceMapper {
    AssessmentInstanceEntity toEntity(AssessmentInstance assessmentInstance);

    AssessmentInstance toDomain(AssessmentInstanceEntity assessmentInstanceEntity);
}