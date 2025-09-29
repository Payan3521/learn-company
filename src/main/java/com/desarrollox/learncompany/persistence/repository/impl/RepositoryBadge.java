package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryBadge;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.persistence.mapper.BadgeMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryBadge;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryBadge implements IRepositoryBadge{

    private final JpaRepositoryBadge jpaRepositoryBadge;
    private final BadgeMapper badgeMapper;
    
    
    @Override
    public Badge save(Badge badge) {
        return badgeMapper.toDomain(
                jpaRepositoryBadge.save(
                        badgeMapper.toEntity(badge)
                )
        );
    }

    @Override
    public Optional<Badge> findById(Long id) {
        return jpaRepositoryBadge.findById(id)
                .map(badgeMapper::toDomain);
    }

    @Override
    public List<Badge> findAll() {
        return jpaRepositoryBadge.findAll()
                .stream()
                .map(badgeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Badge> findBadgesByEmployeeId(Long employeeId) {
        return jpaRepositoryBadge.findBadgesByEmployeeId(employeeId)
                .stream()
                .map(badgeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Badge> findBadgeByName(String name) {
        return jpaRepositoryBadge.findBadgeByName(name)
                .map(badgeMapper::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositoryBadge.existsById(id);
    }
    
}