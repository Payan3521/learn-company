package com.desarrollox.learncompany.api_inscriptions.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.api_inscriptions.web.dto.InscriptionRequest;
import com.desarrollox.learncompany.api_inscriptions.web.dto.InscriptionResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inscriptions")
@RequiredArgsConstructor
public class InscriptionController {

    @PostMapping("/create-inscription")
    public ResponseEntity<ApiResponse<InscriptionResponse>> createInscription(
        @RequestBody InscriptionRequest inscriptionRequest){

            return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Inscripcion registrado correctamente", null));
    }

    @GetMapping("/getInscriptionById/{id}")
    public ResponseEntity<ApiResponse<InscriptionResponse>> getByIdInscription(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Inscripción encontrado", null));
    }

    @DeleteMapping("/delete-inscription/{id}")
    public ResponseEntity<ApiResponse<InscriptionResponse>> deleteInscription(@PathVariable Long id){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Inscripcion eliminada", null));
    }

    
}

