package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.web.dto.ModuleRequest;
import com.desarrollox.learncompany.web.dto.ModuleResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AssessmentTemplateWebMapper.class})
public interface ModuleWebMapper {
    
    @Mapping(target = "id", ignore = true)
    Module requestToDomain(ModuleRequest request);
    
    ModuleResponse domainToResponse(Module domain);
}