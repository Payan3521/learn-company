package com.desarrollox.learncompany.web.webMapper;

import com.desarrollox.learncompany.domain.model.Answer;
import com.desarrollox.learncompany.web.dto.AnswerRequest;
import com.desarrollox.learncompany.web.dto.AnswerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {QuestionWebMapper.class})
public interface AnswerWebMapper {

    @Mapping(target = "question", source = "questionId")
    Answer toDomain(AnswerRequest answerRequest);
    
    AnswerResponse toResponse(Answer answer);
}
