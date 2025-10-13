package com.desarrollox.learncompany.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.InscriptionEntity;

@Repository
public interface JpaRepositoryInscription extends JpaRepository<InscriptionEntity, Long>{

    @Query(
        value = "SELECT * FROM inscriptions WHERE employee_id = :employeeId",
        nativeQuery = true
    )
    List<InscriptionEntity> findInscriptionsByEmployeeId(@Param("employeeId") Long employeeId);

    @Query(
        value = "SELECT * FROM inscriptions WHERE course_id = :courseId",
        nativeQuery = true
    )
    List<InscriptionEntity> findInscriptionsByCourseId(@Param("courseId") Long courseId);
}