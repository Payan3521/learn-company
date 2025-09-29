package com.desarrollox.learncompany.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.EmployeeEntity;
import com.desarrollox.learncompany.persistence.entity.UserEntity;

@Repository
public interface JpaRepositoryUser extends JpaRepository<UserEntity, Long>{

    @Query(value = "SELECT * FROM employees e " +
               "WHERE e.department_id = :departmentId " +
               "ORDER BY e.puntos DESC",
                nativeQuery = true)
    List<EmployeeEntity> findByDepartmentOrderByPuntosDesc(@Param("departmentId") Long departmentId);

    @Query(value = "SELECT * FROM users u " +
               "WHERE (:departmentId IS NULL OR u.department_id = :departmentId) " +
               "AND (:role IS NULL OR u.role = :role) " +
               "AND (:status IS NULL OR u.status = :status)",
                  nativeQuery = true)
    Optional<UserEntity> findByFilters(@Param("departmentId") Long departmentId, @Param("role") String role, @Param("status") boolean status);

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
    
}