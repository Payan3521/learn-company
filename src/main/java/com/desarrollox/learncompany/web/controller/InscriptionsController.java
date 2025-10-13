package com.desarrollox.learncompany.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.domain.service.IInscriptionService;
import com.desarrollox.learncompany.web.dto.InscriptionRequest;
import com.desarrollox.learncompany.web.dto.InscriptionResponse;
import com.desarrollox.learncompany.web.webMapper.InscriptionWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inscriptions")
@RequiredArgsConstructor
public class InscriptionsController {
    
    private final IInscriptionService inscriptionService;
    private final InscriptionWebMapper inscriptionWebMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<InscriptionResponse>> createCourse(@Valid @RequestBody InscriptionRequest request){
        Inscription inscription = inscriptionWebMapper.requestToDomain(request);
        Inscription inscriptionSaved = inscriptionService.createInscription(inscription);
        InscriptionResponse response = inscriptionWebMapper.domainToResponse(inscriptionSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Inscripcion craeada correctamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InscriptionResponse>> getInscriptionById(@PathVariable Long id){
        Inscription inscription = inscriptionService.getInscriptionById(id).get();
        InscriptionResponse inscriptionResponse = inscriptionWebMapper.domainToResponse(inscription);
        return ResponseEntity.ok(ApiResponse.success("Inscripcion encontrada", inscriptionResponse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<InscriptionResponse>> deleteInscription(@PathVariable Long id){
        Inscription inscriptionDeleted = inscriptionService.deleteInscription(id).get();
        InscriptionResponse inscriptionResponse = inscriptionWebMapper.domainToResponse(inscriptionDeleted);
        return ResponseEntity.ok(ApiResponse.success("Inscripcion eliminada correctamente", inscriptionResponse)); 
    }

    //optener las inscriptciones curso id
    //optener las inscriptciones mpleado id

}