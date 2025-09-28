package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Badge;

public interface IBadgeService {
    Badge createBadge(Badge badge);
    Optional<Badge> getBadgeById(Long id);
    List<Badge> getAllBadges();
    List<Badge> getBadgesByEmployeeId(Long employeeId);
    Optional<Badge> findByName(String name);
}