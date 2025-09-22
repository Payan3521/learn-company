package com.desarrollox.learncompany.persistence.repository.mapper;

import org.mapstruct.Mapper;
import com.desarrollox.learncompany.domain.model.Certificate;
import com.desarrollox.learncompany.persistence.entity.CertificateEntity;

@Mapper(componentModel = "spring")
public interface CertificateMapper {
    CertificateEntity toCertificateEntity(Certificate certificate);
    Certificate toCertificate(CertificateEntity entity);
}