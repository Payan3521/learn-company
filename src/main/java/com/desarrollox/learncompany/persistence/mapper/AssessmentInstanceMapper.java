package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.persistence.entity.AssessmentInstanceEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AssessmentTemplateMapper.class, EmployeeMapper.class, AnswerMapper.class})
public interface AssessmentInstanceMapper {
    
    AssessmentInstance toDomain(AssessmentInstanceEntity entity);
    AssessmentInstanceEntity toEntity(AssessmentInstance domain);
}