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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final ICertificateService certificateService;
    private final CertificateWebMapper certificateWebMapper;
    
    @Operation(
        summary = "Crear un nuevo certificado",
        description = "Permite a los instructores crear un nuevo certificado.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo solicitud",
                    value = """
                        {
                            "employeeId": 2,
                            "courseId": 1
                        }
                        """
                )
            )
        ),
        responses =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Certificado creado exitosamente",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitoso",
                        value = """
                            { 
                                "success": true,
                                "message": "Certificado creado exitosamente",
                                "data": {
                                    "id": 1,
                                    "employeeId": 2,
                                    "courseId": 1,
                                    "dateIssued": "2025-10-18T17:01:46.472393097"
                                },
                                "timestamp": "2025-10-18T17:01:46.497820077"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Objeto no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor")
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<CertificateResponse>> createCertificate(@Valid @RequestBody CertificateRequest request){
        Certificate certificate = certificateWebMapper.requestToDomain(request);
        Certificate savedCertificate = certificateService.createCertificate(certificate);
        CertificateResponse response = certificateWebMapper.domainToResponse(savedCertificate);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Certificado creado exitosamente", response));
    }

    @Operation(
        summary = "Obtener todos los certificados",
        description = "Devuelve la lista completa de certificados registrados.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Lista de certificados",
                                "data": [
                                    {
                                        "id": 1,
                                        "employeeId": 2,
                                        "courseId": 1,
                                        "dateIssued": "2025-10-18T17:01:46"
                                    },
                                    {
                                        "id": 3,
                                        "employeeId": 3,
                                        "courseId": 1,
                                        "dateIssued": "2025-10-18T17:07:24"
                                    }
                                ],
                                "timestamp": "2025-10-18T17:07:34.268135315"
                            }
                            
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista sin contenido"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor")
        }
    )
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

    @Operation(
        summary = "Obtener certificados por id de empleado",
        description = "Busca y devuelve un empleado por el id de un empleado especifico",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del empleado",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Certificado encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Lista de certificados del usuario con id: 3",
                                "data": [
                                    {
                                        "id": 3,
                                        "employeeId": 3,
                                        "courseId": 1,
                                        "dateIssued": "2025-10-18T17:07:24"
                                    }
                                ],
                                "timestamp": "2025-10-18T17:12:03.184903052"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor")
        }
    )
    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<List<CertificateResponse>>> getEmployeeById(@PathVariable Long id){
        List<Certificate> certificates = certificateService.getCertificatesByUserId(id);

        if(certificates.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<CertificateResponse> responses = certificates.stream()
                .map(certificateWebMapper::domainToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success("Lista de certificados del usuario con id: " + id, responses));
        
    }

    @Operation(
        summary = "Obtener certificados por id",
        description = "Busca y devuelve un certificado por su id en especifico",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de la certificado",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Certificado encontrado",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Certificado con id: 1",
                                "data": {
                                    "id": 1,
                                    "employeeId": 2,
                                    "courseId": 1,
                                    "dateIssued": "2025-10-18T17:01:46"
                                },
                                "timestamp": "2025-10-18T17:14:48.432418577"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Certificado no encontrado"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor")
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CertificateResponse>> getCertificatesById(@PathVariable Long id) {
        Certificate certificate = certificateService.getCertificateById(id).get();
        CertificateResponse response = certificateWebMapper.domainToResponse(certificate);
        return ResponseEntity.ok(ApiResponse.success("Certificado con id: " + id, response));
    }
    
}