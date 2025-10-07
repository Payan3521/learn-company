package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.persistence.entity.ModuleEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CourseMapper.class})
public interface ModuleMapper {
    
    @Mapping(target = "assessmentTemplate", ignore = true)
    Module toDomain(ModuleEntity entity);

    @Mapping(target = "assessmentTemplate", ignore = true)
    ModuleEntity toEntity(Module domain);
}