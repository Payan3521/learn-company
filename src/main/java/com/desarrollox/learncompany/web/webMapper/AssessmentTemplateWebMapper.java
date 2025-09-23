package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.web.dto.AssessmentTemplateRequest;
import com.desarrollox.learncompany.web.dto.AssessmentTemplateResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ModuleWebMapper.class, QuestionWebMapper.class})
public interface AssessmentTemplateWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module.id", source = "moduleId")
    @Mapping(target = "module.course", ignore = true)
    @Mapping(target = "module.title", ignore = true)
    @Mapping(target = "module.assessmentTemplate", ignore = true)
    @Mapping(source = "questions", target = "questions")
    AssessmentTemplate requestToDomain(AssessmentTemplateRequest request);
    
    AssessmentTemplateResponse domainToResponse(AssessmentTemplate domain);
}