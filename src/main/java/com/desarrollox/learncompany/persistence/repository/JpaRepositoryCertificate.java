package com.desarrollox.learncompany.persistence.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.CertificateEntity;

@Repository
public interface JpaRepositoryCertificate extends JpaRepository<CertificateEntity, Long> {

    @Query(
        value = "SELECT * FROM certificates c WHERE c.employee_id = :employeeId",
        nativeQuery = true
    )
    List<CertificateEntity> findByUserId(@Param("employeeId") Long userId);
    
}