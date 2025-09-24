package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.web.dto.AssessmentTemplateRequest;
import com.desarrollox.learncompany.web.dto.AssessmentTemplateResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {QuestionWebMapper.class})
public interface AssessmentTemplateWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "module", ignore = true)
    AssessmentTemplate requestToDomain(AssessmentTemplateRequest request);
    
    @Mapping(target = "moduleId", source = "module.id")
    AssessmentTemplateResponse domainToResponse(AssessmentTemplate domain);
}