package com.desarrollox.learncompany.persistence.repository;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.User.Role;
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

    @Query("SELECT u FROM UserEntity u " +
       "WHERE (:departmentId IS NULL OR u.department.id = :departmentId) " +
       "AND (:role IS NULL OR u.role = :role) " +
       "AND u.status = :status")
    List<UserEntity> findByFilters(@Param("departmentId") Long departmentId, @Param("role") Role role, @Param("status") boolean status);

    Optional<UserEntity> findByEmailAndStatusTrue(String email);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIdAndStatusTrue(Long id);

    boolean existsByEmailAndStatusTrue(String email);
    
    boolean existsByIdAndStatusTrue(Long id);

    List<UserEntity> findAllByStatusTrue();

    @Query(value = """
        SELECT DISTINCT 
            u.id, 
            u.email, 
            u.password, 
            u.name, 
            u.lastname, 
            u.status, 
            u.role, 
            u.department_id, 
            u.url_photo,
            e.puntos
        FROM certificates c
        INNER JOIN employees e ON c.employee_id = e.id
        INNER JOIN users u ON e.id = u.id
    """, nativeQuery = true)
    List<EmployeeEntity> findEmployeesFinished();

    @Query(value = """
        SELECT 
            u.id, 
            u.email, 
            u.password, 
            u.name, 
            u.lastname, 
            u.status, 
            u.role, 
            u.department_id, 
            u.url_photo,
            u.created_at,
            u.updated_at,
            e.puntos
        FROM certificates c
        INNER JOIN employees e ON c.employee_id = e.id
        INNER JOIN users u ON e.id = u.id
        WHERE c.course_id = :courseId
    """, nativeQuery = true)
    List<EmployeeEntity> findEmployeesFinishedByCourseId(@Param("courseId") Long courseId);
    
}