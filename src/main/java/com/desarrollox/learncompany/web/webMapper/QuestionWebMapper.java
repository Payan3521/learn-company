package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Question;
import com.desarrollox.learncompany.web.dto.QuestionRequest;
import com.desarrollox.learncompany.web.dto.QuestionResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "assessmentTemplate", ignore = true)
    Question requestToDomain(QuestionRequest request);
    
    QuestionResponse domainToResponse(Question domain);
}