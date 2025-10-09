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
    
    @PostMapping
    public ResponseEntity<ApiResponse<CertificateResponse>> createCertificate(@Valid @RequestBody CertificateRequest request){
        Certificate certificate = certificateWebMapper.requestToDomain(request);
        Certificate savedCertificate = certificateService.createCertificate(certificate);
        CertificateResponse response = certificateWebMapper.domainToResponse(savedCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Certificado creado exitosamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CertificateResponse>>> getAllCertificates(){
        List<Certificate> certificates = certificateService.getAllCertificates();

        if(certificates.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<CertificateResponse> responses = certificates.stream()
                .map(certificateWebMapper::domainToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success("Lista de certificados", responses));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<List<CertificateResponse>>> getUsersById(@PathVariable Long id){
        List<Certificate> certificates = certificateService.getCertificatesByUserId(id);

        if(certificates.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<CertificateResponse> responses = certificates.stream()
                .map(certificateWebMapper::domainToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success("Lista de certificados del usuario con id: " + id, responses));
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CertificateResponse>> getCertificatesById(@PathVariable Long id) {
        Certificate certificate = certificateService.getCertificateById(id).get();
        CertificateResponse response = certificateWebMapper.domainToResponse(certificate);
        return ResponseEntity.ok(ApiResponse.success("Certificado con id: " + id, response));
    }
    
}