package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.persistence.entity.InscriptionEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EmployeeMapper.class, CourseMapper.class})
public interface InscriptionMapper {
    
    Inscription toDomain(InscriptionEntity entity);
    
    @Mapping(source = "employee", target = "employee")
    @Mapping(target = "id", ignore = true)
    InscriptionEntity toEntity(Inscription domain);
}