package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Question;
import com.desarrollox.learncompany.persistence.entity.QuestionEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AssessmentTemplateMapper.class})
public interface QuestionMapper {
    
    Question toDomain(QuestionEntity entity);
    QuestionEntity toEntity(Question domain);
}