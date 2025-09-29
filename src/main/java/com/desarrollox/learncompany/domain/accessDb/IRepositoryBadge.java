package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Badge;

public interface IRepositoryBadge {
    Badge save(Badge badge);
    Optional<Badge> findById(Long id);
    List<Badge> findAll();
    List<Badge> findBadgesByEmployeeId(Long employeeId);
    Optional<Badge> findBadgeByName(String name);
    boolean existsById(Long id);
}