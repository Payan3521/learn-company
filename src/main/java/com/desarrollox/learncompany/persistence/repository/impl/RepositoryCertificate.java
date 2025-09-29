package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCertificate;
import com.desarrollox.learncompany.domain.model.Certificate;
import com.desarrollox.learncompany.persistence.mapper.CertificateMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryCertificate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryCertificate implements IRepositoryCertificate{

    private final JpaRepositoryCertificate jpaRepositoryCertificate;
    private final CertificateMapper certificateMapper;
    
    @Override
    public Certificate save(Certificate certificate) {
        return certificateMapper.toDomain(jpaRepositoryCertificate.save(certificateMapper.toEntity(certificate)));
    }

    @Override
    public List<Certificate> findAll() {
        return jpaRepositoryCertificate.findAll().stream().map(certificateMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        return jpaRepositoryCertificate.findById(id).map(certificateMapper::toDomain);
    }

    @Override
    public List<Certificate> findCertificatesByUserId(Long userId) {
        return jpaRepositoryCertificate.findByUserId(userId).stream().map(certificateMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositoryCertificate.existsById(id);
    }
    
}