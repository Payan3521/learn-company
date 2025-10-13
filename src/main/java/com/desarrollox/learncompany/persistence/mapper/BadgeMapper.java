package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.persistence.entity.BadgeEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, 
    uses = {EmployeeMapper.class})
public interface BadgeMapper {
    
    Badge toDomain(BadgeEntity entity);

    BadgeEntity toEntity(Badge domain);
}