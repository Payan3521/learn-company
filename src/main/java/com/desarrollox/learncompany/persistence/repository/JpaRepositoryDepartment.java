package com.desarrollox.learncompany.persistence.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.DepartmentEntity;

@Repository
public interface JpaRepositoryDepartment extends JpaRepository<DepartmentEntity, Long> {

    @Query(value = "SELECT * FROM departments d WHERE " +
           "(:name IS NULL OR d.name LIKE %:name%) AND " +
           "(:hierarchy = 0 OR d.hierarchy = :hierarchy)",
             nativeQuery = true)
    Optional<DepartmentEntity> findByNameContainingAndHierarchy(@Param("name") String name, @Param("hierarchy") int hierarchy);
    
}
