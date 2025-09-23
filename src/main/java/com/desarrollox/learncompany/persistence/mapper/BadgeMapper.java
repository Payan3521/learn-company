package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.persistence.entity.BadgeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BadgeMapper {

    BadgeEntity toEntity(Badge badge);

    Badge toDomain(BadgeEntity badgeEntity);
}