package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCertificate;
import com.desarrollox.learncompany.domain.model.Certificate;
import com.desarrollox.learncompany.persistence.mapper.CertificateMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryCertificate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryCertificate implements IRepositoryCertificate {

    private final JpaRepositoryCertificate jpaRepositoryCertificate;
    private final CertificateMapper certificateMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public Certificate save(Certificate certificate) {
        loggingService.logInfo("Iniciando guardado de Certificate para userId: {}", 
                certificate.getEmployee().getId() != null ? certificate.getEmployee().getId() : "null");
        try {
            Certificate savedCertificate = certificateMapper.toDomain(
                    jpaRepositoryCertificate.save(
                            certificateMapper.toEntity(certificate)
                    )
            );
            loggingService.logInfo("Certificate guardado exitosamente con ID: {} para userId: {}", 
                    savedCertificate.getId(), savedCertificate.getEmployee().getId());
            return savedCertificate;
        } catch (Exception e) {
            loggingService.logError("Error al guardar Certificate para userId {}: {}", 
                    certificate.getEmployee().getId() != null ? certificate.getEmployee().getId() : "null", 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Certificate> findAll() {
        loggingService.logInfo("Obteniendo todos los Certificates");
        try {
            List<Certificate> certificates = jpaRepositoryCertificate.findAll()
                    .stream()
                    .map(certificateMapper::toDomain)
                    .collect(Collectors.toList());
            if (certificates.isEmpty()) {
                loggingService.logWarning("No se encontraron Certificates");
            } else {
                loggingService.logInfo("Se encontraron {} Certificates", certificates.size());
            }
            return certificates;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Certificates: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Certificate> findById(Long id) {
        loggingService.logInfo("Obteniendo Certificate con ID: {}", id);
        try {
            Optional<Certificate> certificate = jpaRepositoryCertificate.findById(id)
                    .map(certificateMapper::toDomain);
            if (certificate.isPresent()) {
                loggingService.logInfo("Certificate ID {} obtenido exitosamente", id);
            } else {
                loggingService.logWarning("Certificate con ID {} no encontrado", id);
            }
            return certificate;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Certificate ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Certificate> findCertificatesByUserId(Long userId) {
        loggingService.logInfo("Obteniendo Certificates para userId: {}", userId);
        try {
            List<Certificate> certificates = jpaRepositoryCertificate.findByUserId(userId)
                    .stream()
                    .map(certificateMapper::toDomain)
                    .collect(Collectors.toList());
            if (certificates.isEmpty()) {
                loggingService.logWarning("No se encontraron Certificates para userId: {}", userId);
            } else {
                loggingService.logInfo("Se encontraron {} Certificates para userId: {}", certificates.size(), userId);
            }
            return certificates;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Certificates para userId {}: {}", userId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de Certificate con ID: {}", id);
        try {
            boolean exists = jpaRepositoryCertificate.existsById(id);
            loggingService.logDebug("Certificate con ID {} existe: {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de Certificate ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}