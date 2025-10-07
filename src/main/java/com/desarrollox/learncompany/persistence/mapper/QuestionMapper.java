package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Question;
import com.desarrollox.learncompany.persistence.entity.QuestionEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuestionMapper {
    
    @Mapping(target = "assessmentTemplate", ignore = true)
    Question toDomain(QuestionEntity entity);

    @Mapping(target = "assessmentTemplate", ignore = true)
    QuestionEntity toEntity(Question domain);
}