package com.desarrollox.learncompany.persistence.repository.mapper;

import org.mapstruct.Mapper;
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.persistence.entity.InscriptionEntity;

@Mapper(componentModel = "spring")
public interface InscriptionMapper {
    InscriptionEntity toInscriptionEntity(Inscription inscription);
    Inscription toInscription(InscriptionEntity entity);
}