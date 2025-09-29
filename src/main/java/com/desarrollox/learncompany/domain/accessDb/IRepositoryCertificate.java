package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Certificate;

public interface IRepositoryCertificate {
    Certificate save(Certificate certificate);
    List<Certificate> findAll();
    Optional<Certificate> findById(Long id);
    List<Certificate> findCertificatesByUserId(Long userId);
    boolean existsById(Long id);
}