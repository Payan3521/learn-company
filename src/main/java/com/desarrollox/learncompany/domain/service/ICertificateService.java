package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Certificate;

public interface ICertificateService {
    Certificate createCertificate(Certificate certificate);
    List<Certificate> getAllCertificates();
    Optional<Certificate> getCertificateById(Long id);
    List<Certificate> getCertificatesByUserId(Long userId);
} 