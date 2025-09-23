package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.Administrator;
import com.desarrollox.learncompany.persistence.entity.AdministratorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AdministratorMapper {

    AdministratorEntity toAdministratorEntity(Administrator administrator);

    Administrator toAdministrator(AdministratorEntity administratorEntity);
}