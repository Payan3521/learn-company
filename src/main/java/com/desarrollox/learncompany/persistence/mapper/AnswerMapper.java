package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import com.desarrollox.learncompany.domain.model.Answer;
import com.desarrollox.learncompany.persistence.entity.AnswerEntity;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class, AssessmentInstanceMapper.class})
public interface AnswerMapper {
    AnswerEntity toEntity(Answer answer);
    Answer toDomain(AnswerEntity answerEntity);
}