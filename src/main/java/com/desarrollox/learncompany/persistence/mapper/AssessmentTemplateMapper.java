package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.persistence.entity.AssessmentTemplateEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ModuleMapper.class, QuestionMapper.class})
public interface AssessmentTemplateMapper {
    
    AssessmentTemplate toDomain(AssessmentTemplateEntity entity);
    
    AssessmentTemplateEntity toEntity(AssessmentTemplate domain);
}