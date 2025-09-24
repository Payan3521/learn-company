package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Answer;
import com.desarrollox.learncompany.persistence.entity.AnswerEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {QuestionMapper.class, AssessmentInstanceMapper.class})
public interface AnswerMapper {
    
    Answer toDomain(AnswerEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateIssued", ignore = true)
    AnswerEntity toEntity(Answer domain);
}