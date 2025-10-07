package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.persistence.entity.AssessmentTemplateEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssessmentTemplateMapper {
    
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "questions", ignore = true)
    AssessmentTemplate toDomain(AssessmentTemplateEntity entity);
    
    @Mapping(target = "module", ignore = true)
    @Mapping(target = "questions", ignore = true)
    AssessmentTemplateEntity toEntity(AssessmentTemplate domain);
}