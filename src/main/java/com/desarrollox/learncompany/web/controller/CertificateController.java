package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Certificate;
import com.desarrollox.learncompany.domain.service.ICertificateService;
import com.desarrollox.learncompany.web.dto.CertificateRequest;
import com.desarrollox.learncompany.web.dto.CertificateResponse;
import com.desarrollox.learncompany.web.webMapper.CertificateWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final ICertificateService certificateService;
    private final CertificateWebMapper certificateWebMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @PostMapping
    public ResponseEntity<ApiResponse<CertificateResponse>> createCertificate(@Valid @RequestBody CertificateRequest request) {
        loggingService.logInfo("Iniciando creación de Certificate para userId: {}", 
                request != null && request.getEmployeeId() != null ? request.getEmployeeId() : "null");
        try {
            Certificate certificate = certificateWebMapper.requestToDomain(request);
            Certificate savedCertificate = certificateService.createCertificate(certificate);
            CertificateResponse response = certificateWebMapper.domainToResponse(savedCertificate);
            loggingService.logInfo("Certificate creado exitosamente con ID: {} para userId: {}", 
                    savedCertificate.getId(), 
                    savedCertificate.getEmployee().getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Certificado creado exitosamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al crear Certificate para userId {}: {}", 
                    request != null && request.getEmployeeId() != null ? request.getEmployeeId() : "null", 
                    e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CertificateResponse>>> getAllCertificates() {
        loggingService.logInfo("Obteniendo todos los Certificates");
        try {
            List<Certificate> certificates = certificateService.getAllCertificates();

            if (certificates.isEmpty()) {
                loggingService.logWarning("No se encontraron Certificates");
                return ResponseEntity.noContent().build();
            }

            List<CertificateResponse> responses = certificates.stream()
                    .map(certificateWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Certificates", responses.size());
            return ResponseEntity.ok(ApiResponse.success("Lista de certificados", responses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Certificates: {}", e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<List<CertificateResponse>>> getUsersById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Certificates para userId: {}", id);
        try {
            List<Certificate> certificates = certificateService.getCertificatesByUserId(id);

            if (certificates.isEmpty()) {
                loggingService.logWarning("No se encontraron Certificates para userId: {}", id);
                return ResponseEntity.noContent().build();
            }

            List<CertificateResponse> responses = certificates.stream()
                    .map(certificateWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Certificates para userId: {}", responses.size(), id);
            return ResponseEntity.ok(ApiResponse.success("Lista de certificados del usuario con id: " + id, responses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Certificates para userId {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CertificateResponse>> getCertificatesById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Certificate con ID: {}", id);
        try {
            Certificate certificate = certificateService.getCertificateById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Certificate con ID " + id + " no encontrado"));
            CertificateResponse response = certificateWebMapper.domainToResponse(certificate);
            loggingService.logInfo("Certificate ID {} obtenido exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Certificado con id: " + id, response));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Certificate ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // Método auxiliar para truncar nombres de certificados en los logs (si se necesita en el futuro)
    private String truncateName(String name) {
        if (name == null) {
            return "null";
        }
        return name.length() > 30 ? name.substring(0, 30) + "..." : name;
    }
}