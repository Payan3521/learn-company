package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.persistence.entity.AssessmentTemplateEntity;

@Mapper(componentModel = "spring", uses = {ModuleMapper.class, QuestionMapper.class})
public interface AssessmentTemplateMapper {

    AssessmentTemplateEntity toEntity(AssessmentTemplate assessmentTemplate);

    AssessmentTemplate toDomain(AssessmentTemplateEntity assessmentTemplateEntity);
}