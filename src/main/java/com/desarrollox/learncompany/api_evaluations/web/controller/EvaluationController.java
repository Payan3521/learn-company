package com.desarrollox.learncompany.api_evaluations.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.api_evaluations.web.dto.EvaluationRequest;
import com.desarrollox.learncompany.api_evaluations.web.dto.EvaluationResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/evaluations")
@RequiredArgsConstructor
public class EvaluationController {

    @PostMapping("/create-evaluation-instance")
    public ResponseEntity<ApiResponse<EvaluationResponse>> createEvaluation(
        @RequestBody EvaluationRequest evaluationRequest){

            return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Evaluacion registrado correctamente", null));
    }

    @GetMapping("/getById/notas/{id}")
    public ResponseEntity<ApiResponse<EvaluationResponse>> getByIdNotes(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Nota encontrada", null));
    }

    @GetMapping("/getEvaluationById/{id}")
    public ResponseEntity<ApiResponse<EvaluationResponse>> getByIdEvaluation(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Evaluacion encontrada", null));
    }

     @GetMapping("/getById-feedback/{id}")
    public ResponseEntity<ApiResponse<EvaluationResponse>> getByIdFeedBack(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Revision encontrada", null));
    }

}
