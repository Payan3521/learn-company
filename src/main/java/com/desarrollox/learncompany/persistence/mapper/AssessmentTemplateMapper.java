package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.persistence.entity.AssessmentTemplateEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ModuleMapper.class})
public interface AssessmentTemplateMapper {
    
    @Mapping(target = "questions", ignore = true)
    AssessmentTemplate toDomain(AssessmentTemplateEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "questions", ignore = true)
    AssessmentTemplateEntity toEntity(AssessmentTemplate domain);
}