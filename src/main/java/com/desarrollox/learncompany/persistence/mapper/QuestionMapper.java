package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import com.desarrollox.learncompany.domain.model.Question;
import com.desarrollox.learncompany.persistence.entity.QuestionEntity;

@Mapper(componentModel = "spring", uses = {AssessmentTemplateMapper.class})
public interface QuestionMapper {

    QuestionEntity toEntity(Question question);

    Question toDomain(QuestionEntity questionEntity);
}