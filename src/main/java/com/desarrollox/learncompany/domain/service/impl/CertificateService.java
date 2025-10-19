package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCertificate;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.CertificateNotFoundException;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Certificate;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.service.ICertificateService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateService implements ICertificateService {

    private final IRepositoryCertificate repositoryCertificate;
    private final IRepositoryUser repositoryUser;
    private final IRepositoryCourse repositoryCourse;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = false)
    @Override
    public Certificate createCertificate(Certificate certificate) {
        loggingService.logInfo("Iniciando creación de Certificate para employeeId: {} y courseId: {}", 
                certificate.getEmployee().getId(), certificate.getCourse().getId());
        try {
            if (!repositoryCourse.existsById(certificate.getCourse().getId())) {
                loggingService.logError("Curso con ID {} no encontrado", certificate.getCourse().getId());
                throw new CourseNotFoundException(certificate.getCourse().getId());
            }
            if (!repositoryUser.existsById(certificate.getEmployee().getId())) {
                loggingService.logError("Usuario con ID {} no encontrado", certificate.getEmployee().getId());
                throw new UserNotFoundException(certificate.getEmployee().getId());
            }
            if (!repositoryUser.findById(certificate.getEmployee().getId()).get().isEmployee()) {
                loggingService.logError("El usuario con ID {} no tiene rol EMPLOYEE", certificate.getEmployee().getId());
                throw new InvalidRoleException("El usuario con ID " + certificate.getEmployee().getId() + " no tiene rol de EMPLOYEE");
            }

            loggingService.logDebug("Obteniendo entidades para Certificate: courseId={}, employeeId={}", 
                    certificate.getCourse().getId(), certificate.getEmployee().getId());
            certificate.setCourse(repositoryCourse.findById(certificate.getCourse().getId()).get());
            certificate.setEmployee((Employee) repositoryUser.findById(certificate.getEmployee().getId()).get());

            Certificate savedCertificate = repositoryCertificate.save(certificate);
            loggingService.logInfo("Certificate creado exitosamente con ID: {}", savedCertificate.getId());
            return savedCertificate;
        } catch (Exception e) {
            loggingService.logError("Error al crear Certificate para employeeId {} y courseId {}: {}", 
                    certificate.getEmployee().getId(), certificate.getCourse().getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Certificate> getAllCertificates() {
        loggingService.logInfo("Obteniendo todos los Certificates");
        try {
            List<Certificate> certificates = repositoryCertificate.findAll();
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

    @Transactional(readOnly = true)
    @Override
    public Optional<Certificate> getCertificateById(Long id) {
        loggingService.logInfo("Obteniendo Certificate con ID: {}", id);
        try {
            if (!repositoryCertificate.existsById(id)) {
                loggingService.logError("Certificate con ID {} no encontrado", id);
                throw new CertificateNotFoundException(id);
            }
            Optional<Certificate> certificate = repositoryCertificate.findById(id);
            loggingService.logInfo("Certificate ID {} obtenido exitosamente", id);
            return certificate;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Certificate ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Certificate> getCertificatesByUserId(Long userId) {
        loggingService.logInfo("Obteniendo Certificates para userId: {}", userId);
        try {
            if (!repositoryUser.existsById(userId)) {
                loggingService.logError("Usuario con ID {} no encontrado", userId);
                throw new UserNotFoundException(userId);
            }
            List<Certificate> certificates = repositoryCertificate.findCertificatesByUserId(userId);
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
}