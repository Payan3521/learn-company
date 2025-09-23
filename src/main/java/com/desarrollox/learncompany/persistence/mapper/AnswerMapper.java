package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Answer;
import com.desarrollox.learncompany.persistence.entity.AnswerEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {QuestionMapper.class, AssessmentInstanceMapper.class})
public interface AnswerMapper {
    
    Answer toDomain(AnswerEntity entity);
    AnswerEntity toEntity(Answer domain);
}