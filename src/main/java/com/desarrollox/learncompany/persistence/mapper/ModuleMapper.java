package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.persistence.entity.ModuleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CourseMapper.class, AssessmentTemplateMapper.class})
public interface ModuleMapper {

    ModuleEntity toEntity(Module module);

    Module toDomain(ModuleEntity moduleEntity);
}