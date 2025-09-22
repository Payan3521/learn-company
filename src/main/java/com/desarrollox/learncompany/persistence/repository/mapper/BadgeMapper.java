package com.desarrollox.learncompany.persistence.repository.mapper;

import org.mapstruct.Mapper;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.persistence.entity.BadgeEntity;


@Mapper(componentModel = "spring")
public interface BadgeMapper {
    BadgeEntity toBadgeEntity(Badge badge);
    Badge toBadge(BadgeEntity entity);
}