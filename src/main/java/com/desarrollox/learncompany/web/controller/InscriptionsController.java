package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/inscriptions")
@RequiredArgsConstructor
@Tag(
    name = "Inscriptions",
    description = "Endpoints para la gestión de inscripciones de empleados en cursos dentro de la organización."
)
public class InscriptionsController {
    
    private final IInscriptionService inscriptionService;
    private final InscriptionWebMapper inscriptionWebMapper;

    @Operation(
        summary = "Crear una nueva inscripción",
        description = "Registra la inscripción de un empleado a un curso específico.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo de creación de inscripción",
                    value = """
                    {
                      "employeeId": 4,
                      "courseId": 7,
                      "inscriptionDate": "2025-10-15"
                    }
                    """
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Inscripción creada correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Empleado o curso no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<InscriptionResponse>> createINscription(@Valid @RequestBody InscriptionRequest request){
        Inscription inscription = inscriptionWebMapper.requestToDomain(request);
        Inscription inscriptionSaved = inscriptionService.createInscription(inscription);
        InscriptionResponse response = inscriptionWebMapper.domainToResponse(inscriptionSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Inscripcion craeada correctamente", response));
    }

    @Operation(
        summary = "Obtener una inscripción por su ID",
        description = "Devuelve la información detallada de una inscripción específica.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inscripción encontrada correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Inscripción no encontrada", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InscriptionResponse>> getInscriptionById(@PathVariable Long id){
        Inscription inscription = inscriptionService.getInscriptionById(id).get();
        InscriptionResponse inscriptionResponse = inscriptionWebMapper.domainToResponse(inscription);
        return ResponseEntity.ok(ApiResponse.success("Inscripcion encontrada", inscriptionResponse));
    }

    @Operation(
        summary = "Eliminar una inscripción por su ID",
        description = "Elimina una inscripción previamente registrada.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inscripción eliminada correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Inscripción no encontrada", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<InscriptionResponse>> deleteInscription(@PathVariable Long id){
        Inscription inscriptionDeleted = inscriptionService.deleteInscription(id).get();
        InscriptionResponse inscriptionResponse = inscriptionWebMapper.domainToResponse(inscriptionDeleted);
        return ResponseEntity.ok(ApiResponse.success("Inscripcion eliminada correctamente", inscriptionResponse)); 
    }

    @Operation(
        summary = "Obtener todas las inscripciones de un curso",
        description = "Devuelve la lista de inscripciones asociadas a un curso específico identificado por su ID.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inscripciones del curso obtenidas correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "El curso no tiene inscripciones registradas", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content)
        }
    )
    @GetMapping("/course/{id}")
    public ResponseEntity<ApiResponse<List<InscriptionResponse>>> getInscriptionsByCourseById(@PathVariable Long id){
        List<Inscription> inscriptions = inscriptionService.findByCourseId(id);

        if(inscriptions.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<InscriptionResponse> inscriptionResponses = inscriptions.stream().map(inscriptionWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Obtenidas las inscripciones pertenecientes al curso:" + id, inscriptionResponses ));
    }

    @Operation(
        summary = "Obtener todas las inscripciones de un empleado",
        description = "Devuelve la lista de inscripciones asociadas a un empleado específico identificado por su ID.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Inscripciones del empleado obtenidas correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "El empleado no tiene inscripciones registradas", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Empleado no encontrado", content = @Content)
        }
    )
    @GetMapping("/employee/{id}")
    public ResponseEntity<ApiResponse<List<InscriptionResponse>>> getInscriptionsByEmployeeById(@PathVariable Long id){
        List<Inscription> inscriptions = inscriptionService.findByEmployeeId(id);

        if(inscriptions.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<InscriptionResponse> inscriptionResponses = inscriptions.stream().map(inscriptionWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Obtenidas las inscripciones pertenecientes al empleado:" + id, inscriptionResponses ));
    }

}