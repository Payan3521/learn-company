package com.desarrollox.learncompany.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.BadgeEntity;

@Repository
public interface JpaRepositoryBadge extends JpaRepository<BadgeEntity, Long> {

    @Query(
        value = "SELECT b.* FROM badges b " +
                "INNER JOIN employee_badges eb ON b.id = eb.badge_id " +
                "WHERE eb.employee_id = :employeeId",
        nativeQuery = true
    )
    List<BadgeEntity> findBadgesByEmployeeId(@Param("employeeId") Long employeeId);

    Optional<BadgeEntity> findBadgeByName(String name);
    
}