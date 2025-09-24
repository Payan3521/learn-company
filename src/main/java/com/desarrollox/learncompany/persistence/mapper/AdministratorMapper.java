package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Administrator;
import com.desarrollox.learncompany.persistence.entity.AdministratorEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {DepartmentMapper.class})
public interface AdministratorMapper {
    
    Administrator toDomain(AdministratorEntity entity);

    @Mapping(target = "id", ignore = true)
    AdministratorEntity toEntity(Administrator domain);
}