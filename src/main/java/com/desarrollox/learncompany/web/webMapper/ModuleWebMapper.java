package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.web.dto.ModuleRequest;
import com.desarrollox.learncompany.web.dto.ModuleResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CourseWebMapper.class, AssessmentTemplateWebMapper.class})
public interface ModuleWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course.id", source = "courseId")
    @Mapping(target = "course.modules", ignore = true)
    @Mapping(target = "course.inscriptions", ignore = true)
    @Mapping(target = "course.title", ignore = true)
    @Mapping(target = "course.topic", ignore = true)
    @Mapping(target = "course.description", ignore = true)
    @Mapping(target = "course.level", ignore = true)
    @Mapping(target = "course.duration", ignore = true)
    @Mapping(target = "course.season", ignore = true)
    @Mapping(target = "course.instructor", ignore = true)
    @Mapping(source = "assessmentTemplate", target = "assessmentTemplate")
    Module requestToDomain(ModuleRequest request);
    
    ModuleResponse domainToResponse(Module domain);
}