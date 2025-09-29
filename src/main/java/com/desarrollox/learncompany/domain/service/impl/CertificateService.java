package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCertificate;
import com.desarrollox.learncompany.domain.exception.CertificateNotFoundException;
import com.desarrollox.learncompany.domain.model.Certificate;
import com.desarrollox.learncompany.domain.service.ICertificateService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateService implements ICertificateService {

    private final IRepositoryCertificate repositoryCertificate;

    @Override
    public Certificate createCertificate(Certificate certificate) {
        return repositoryCertificate.save(certificate);
    }

    @Override
    public List<Certificate> getAllCertificates() {
        return repositoryCertificate.findAll();
    }

    @Override
    public Optional<Certificate> getCertificateById(Long id) {

        if(repositoryCertificate.existsById(id)){
            return repositoryCertificate.findById(id);
        }
        
        throw new CertificateNotFoundException(id);
    }

    @Override
    public List<Certificate> getCertificatesByUserId(Long userId) {
        return repositoryCertificate.findCertificatesByUserId(userId);
    }
    
}