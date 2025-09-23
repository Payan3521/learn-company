package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.persistence.entity.ModuleEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CourseMapper.class, AssessmentTemplateMapper.class})
public interface ModuleMapper {
    
    Module toDomain(ModuleEntity entity);
    ModuleEntity toEntity(Module domain);
}