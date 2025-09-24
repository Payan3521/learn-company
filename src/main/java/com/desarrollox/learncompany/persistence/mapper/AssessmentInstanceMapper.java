package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.persistence.entity.AssessmentInstanceEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AssessmentTemplateMapper.class, EmployeeMapper.class})
public interface AssessmentInstanceMapper {
    
    @Mapping(target = "answers", ignore = true)
    AssessmentInstance toDomain(AssessmentInstanceEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "answers", ignore = true)
    AssessmentInstanceEntity toEntity(AssessmentInstance domain);
}