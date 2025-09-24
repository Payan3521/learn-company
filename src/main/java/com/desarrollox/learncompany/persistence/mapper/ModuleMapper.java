package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.persistence.entity.ModuleEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AssessmentTemplateMapper.class})
public interface ModuleMapper {
    
   
    Module toDomain(ModuleEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    ModuleEntity toEntity(Module domain);
}