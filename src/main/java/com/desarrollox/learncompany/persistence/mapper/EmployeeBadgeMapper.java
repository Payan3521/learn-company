package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.EmployeeBadge;
import com.desarrollox.learncompany.persistence.entity.EmployeeBadgeEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BadgeMapper.class, EmployeeMapper.class})
public interface EmployeeBadgeMapper {
    
    EmployeeBadge toDomain(EmployeeBadgeEntity entity);
    EmployeeBadgeEntity toEntity(EmployeeBadge domain);
}