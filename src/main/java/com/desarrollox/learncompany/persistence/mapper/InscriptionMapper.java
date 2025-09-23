package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.persistence.entity.InscriptionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, CourseMapper.class})
public interface InscriptionMapper {

    InscriptionEntity toEntity(Inscription inscription);

    Inscription toDomain(InscriptionEntity inscriptionEntity);
}