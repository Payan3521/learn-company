package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Answer;
import com.desarrollox.learncompany.web.dto.AnswerRequest;
import com.desarrollox.learncompany.web.dto.AnswerResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {QuestionWebMapper.class})
public interface AnswerWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question.id", source = "questionId")
    @Mapping(target = "question.question", ignore = true)
    @Mapping(target = "question.responseOptions", ignore = true)
    @Mapping(target = "question.correctAnswer", ignore = true)
    @Mapping(target = "question.assessmentTemplate", ignore = true)
    @Mapping(target = "dateIssued", ignore = true)
    @Mapping(target = "assessmentInstance", ignore = true)
    Answer requestToDomain(AnswerRequest request);
    
    @Mapping(target = "questionId", source = "question.id")
    @Mapping(target = "assessmentInstanceId", source = "assessmentInstance.id")
    AnswerResponse domainToResponse(Answer domain);
}