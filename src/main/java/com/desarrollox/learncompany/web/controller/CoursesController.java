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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.service.ICourseService;
import com.desarrollox.learncompany.web.dto.CourseRequest;
import com.desarrollox.learncompany.web.dto.CourseResponse;
import com.desarrollox.learncompany.web.webMapper.CourseWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CoursesController {

    private final ICourseService courseService;
    private final CourseWebMapper courseWebMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    
    @Operation(
        summary = "Crear un nuevo curso",
        description = "Permite a los instructores crear un nuevo curso.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Ejemplo solicitud",
                    value = """
                        {
                            "title": "Java for everyone", 
                            "topic": "JAVA", 
                            "description": "Java course", 
                            "level": 3, 
                            "duration": 470, 
                            "seasonId": 1,  
                            "instructorId": 1, 
                            "typeCourse":"OPTIONAL",
                            "departmentId": 1
                        }
                        """
                )
            )
        ),
        responses =  {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Curso creado exitosamente",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitoso",
                        value = """
                            { 
                                "success": true,
                                "message": "Curso creado correctamente",
                                "data": {
                                    "id": 2,
                                    "title": "Java for everyone",
                                    "topic": "JAVA",
                                    "description": "Java course",
                                    "level": 3,
                                    "duration": 470,
                                    "seasonId": 1,
                                    "instructorId": 1,
                                    "typeCourse": "OPTIONAL",
                                    "departmentId": 1
                                },
                                "timestamp": "2025-10-18T17:24:45.766344527"
                            }
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Datos inválidos", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Objeto no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@Valid @RequestBody CourseRequest request) {
        loggingService.logInfo("Iniciando creación de Course con título: {}", 
                truncateTitle(request != null && request.getTitle() != null ? request.getTitle() : "null"));
        try {
            Course course = courseWebMapper.requestToDomain(request);
            Course courseSaved = courseService.createCourse(course);
            CourseResponse response = courseWebMapper.domainToResponse(courseSaved);
            loggingService.logInfo("Course creado exitosamente con ID: {} y título: {}", 
                    courseSaved.getId(), truncateTitle(courseSaved.getTitle()));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Curso creado correctamente", response));
        } catch (Exception e) {
            loggingService.logError("Error al crear Course con título {}: {}", 
                    truncateTitle(request != null && request.getTitle() != null ? request.getTitle() : "null"), 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener todos los cursos",
        description = "Devuelve la lista completa de cursos registrados.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Cursos encontrados",
                                "data": [
                                    {
                                        "id": 1,
                                        "title": "Java for everyone",
                                        "topic": "JAVA",
                                        "description": "Java course",
                                        "level": 3,
                                        "duration": 470,
                                        "seasonId": 1,
                                        "instructorId": 1,
                                        "typeCourse": "OPTIONAL",
                                        "departmentId": 1
                                    },
                                    {
                                        "id": 2,
                                        "title": "Java for everyone",
                                        "topic": "JAVA",
                                        "description": "Java course",
                                        "level": 3,
                                        "duration": 470,
                                        "seasonId": 1,
                                        "instructorId": 1,
                                        "typeCourse": "OPTIONAL",
                                        "departmentId": 1
                                    }
                                ],
                                "timestamp": "2025-10-18T17:26:02.584968875"
                            }
                            
                            """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista sin contenido", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
        }
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses() {
        loggingService.logInfo("Obteniendo todos los Courses");
        try {
            List<Course> courses = courseService.getAllCourses();

            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses");
                return ResponseEntity.noContent().build();
            }

            List<CourseResponse> courseResponses = courses.stream()
                    .map(courseWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Courses", courseResponses.size());
            return ResponseEntity.ok(ApiResponse.success("Cursos encontrados", courseResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Courses: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener curso por id",
        description = "Busca y devuelve un curso por su id en especifico",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador de el curso",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Curso encontrado",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Curso obtenido correctamente",
                                "data": {
                                    "id": 1,
                                    "title": "Java for everyone",
                                    "topic": "JAVA",
                                    "description": "Java course",
                                    "level": 3,
                                    "duration": 470,
                                    "seasonId": 1,
                                    "instructorId": 1,
                                    "typeCourse": "OPTIONAL",
                                    "departmentId": 1
                                },
                                "timestamp": "2025-10-18T17:28:16.994680318"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Course con ID: {}", id);
        try {
            Course course = courseService.getCourseById(id).get();
            CourseResponse courseResponse = courseWebMapper.domainToResponse(course);
            loggingService.logInfo("Course ID {} obtenido exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Curso obtenido correctamente", courseResponse));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Course ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener cursos por id de temporada",
        description = "Busca y devuelve un curso por el id de una temporada especifico",
        parameters = {
            @Parameter(
                name = "season_id",
                description = "Identificador de la temporada",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "curso encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Obtenidos los cursos pertenecientes a la temporada:1",
                                "data": [
                                    {
                                        "id": 1,
                                        "title": "Java for everyone",
                                        "topic": "JAVA",
                                        "description": "Java course",
                                        "level": 3,
                                        "duration": 470,
                                        "seasonId": 1,
                                        "instructorId": 1,
                                        "typeCourse": "OPTIONAL",
                                        "departmentId": 1
                                    },
                                    {
                                        "id": 2,
                                        "title": "Java for everyone",
                                        "topic": "JAVA",
                                        "description": "Java course",
                                        "level": 3,
                                        "duration": 470,
                                        "seasonId": 1,
                                        "instructorId": 1,
                                        "typeCourse": "OPTIONAL",
                                        "departmentId": 1
                                    }
                                ],
                                "timestamp": "2025-10-18T17:31:49.715320405"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Temporada no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista sin contenido", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/season/{seasonId}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesBySeasonId(@PathVariable Long seasonId) {
        loggingService.logInfo("Obteniendo Courses para seasonId: {}", seasonId);
        try {
            List<Course> courses = courseService.getCoursesBySeasonId(seasonId);

            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses para seasonId: {}", seasonId);
                return ResponseEntity.noContent().build();
            }

            List<CourseResponse> courseResponses = courses.stream()
                    .map(courseWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Courses para seasonId: {}", courseResponses.size(), seasonId);
            return ResponseEntity.ok(ApiResponse.success("Obtenidos los cursos pertenecientes a la temporada: " + seasonId, courseResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Courses para seasonId {}: {}", seasonId, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener cursos por id de departamento",
        description = "Busca y devuelve un curso por el id de un departamento especifico",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del departamneto",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Curso encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Cursos optenidos por temporada: 1",
                                "data": [
                                    {
                                        "id": 1,
                                        "title": "Java for everyone",
                                        "topic": "JAVA",
                                        "description": "Java course",
                                        "level": 3,
                                        "duration": 470,
                                        "seasonId": 1,
                                        "instructorId": 1,
                                        "typeCourse": "OPTIONAL",
                                        "departmentId": 1
                                    },
                                    {
                                        "id": 2,
                                        "title": "Java for everyone",
                                        "topic": "JAVA",
                                        "description": "Java course",
                                        "level": 3,
                                        "duration": 470,
                                        "seasonId": 1,
                                        "instructorId": 1,
                                        "typeCourse": "OPTIONAL",
                                        "departmentId": 1
                                    }
                                ],
                                "timestamp": "2025-10-18T17:34:12.143410842"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Departamento no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista sin contenido", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesByDepartmentId(@PathVariable Long departmentId) {
        loggingService.logInfo("Obteniendo Courses para departmentId: {}", departmentId);
        try {
            List<Course> courses = courseService.findByDepartmentId(departmentId);

            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses para departmentId: {}", departmentId);
                return ResponseEntity.noContent().build();
            }

            List<CourseResponse> courseResponses = courses.stream()
                    .map(courseWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Courses para departmentId: {}", courseResponses.size(), departmentId);
            return ResponseEntity.ok(ApiResponse.success("Cursos obtenidos por temporada: " + departmentId, courseResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Courses para departmentId {}: {}", departmentId, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener cursos por titulo",
        description = "Busca y devuelve un curso por su titulo en especifico",
        parameters = {
            @Parameter(
                name = "title",
                description = "Titulo del curso",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Titulo encontrado",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Cursos optenidos",
                                "data": [
                                    {
                                        "id": 1,
                                        "title": "Java for everyone",
                                        "topic": "JAVA",
                                        "description": "Java course",
                                        "level": 3,
                                        "duration": 470,
                                        "seasonId": 1,
                                        "instructorId": 1,
                                        "typeCourse": "OPTIONAL",
                                        "departmentId": 1
                                    },
                                    {
                                        "id": 2,
                                        "title": "Java for everyone",
                                        "topic": "JAVA",
                                        "description": "Java course",
                                        "level": 3,
                                        "duration": 470,
                                        "seasonId": 1,
                                        "instructorId": 1,
                                        "typeCourse": "OPTIONAL",
                                        "departmentId": 1
                                    }
                                ],
                                "timestamp": "2025-10-18T17:39:18.232161005"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista sin contenido", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/by-title")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesByTitle(@RequestParam(required = true) String title) {
        loggingService.logInfo("Obteniendo Courses con título: {}", truncateTitle(title));
        try {
            List<Course> courses = courseService.findByTitleContaining(title);

            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses con título: {}", truncateTitle(title));
                return ResponseEntity.noContent().build();
            }

            List<CourseResponse> courseResponses = courses.stream()
                    .map(courseWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Courses con título: {}", courseResponses.size(), truncateTitle(title));
            return ResponseEntity.ok(ApiResponse.success("Cursos obtenidos", courseResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Courses con título {}: {}", truncateTitle(title), e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener cursos opcionales",
        description = "Busca y devuelve una curso por el id del departamento si es opcional",
        parameters = {
            @Parameter(
                name = "departamentId",
                description = "Identificador del departamento",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Curso encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Cursos opcionales optenidos por temporada: 1",
                                "data": [
                                    {
                                        "id": 1,
                                        "title": "Java for everyone",
                                        "topic": "JAVA",
                                        "description": "Java course",
                                        "level": 3,
                                        "duration": 470,
                                        "seasonId": 1,
                                        "instructorId": 1,
                                        "typeCourse": "OPTIONAL",
                                        "departmentId": 1
                                    },
                                    {
                                        "id": 2,
                                        "title": "Java for everyone",
                                        "topic": "JAVA",
                                        "description": "Java course",
                                        "level": 3,
                                        "duration": 470,
                                        "seasonId": 1,
                                        "instructorId": 1,
                                        "typeCourse": "OPTIONAL",
                                        "departmentId": 1
                                    }
                                ],
                                "timestamp": "2025-10-18T17:43:04.324211395"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista sin contenido", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/optional")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getOptionalsByDepartament(@RequestParam(required = true) Long departmentId) {
        loggingService.logInfo("Obteniendo Courses opcionales para departmentId: {}", departmentId);
        try {
            List<Course> courses = courseService.findByStatusOptional(departmentId);

            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses opcionales para departmentId: {}", departmentId);
                return ResponseEntity.noContent().build();
            }

            List<CourseResponse> courseResponses = courses.stream()
                    .map(courseWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Courses opcionales para departmentId: {}", courseResponses.size(), departmentId);
            return ResponseEntity.ok(ApiResponse.success("Cursos opcionales obtenidos por temporada: " + departmentId, courseResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Courses opcionales para departmentId {}: {}", departmentId, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Obtener cursos obligatorios",
        description = "Busca y devuelve una curso por el id del departamento si es obligatorio",
        parameters = {
            @Parameter(
                name = "departamentId",
                description = "Identificador del departamento",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Curso encontrada",
                content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                        name = "Ejemplo de respuesta exitosa",
                        value = """
                            {
                                "success": true,
                                "message": "Cursos obligatorios optenidos por temporada: 1",
                                "data": [
                                    {
                                        "id": 3,
                                        "title": "Python Xs",
                                        "topic": "Python",
                                        "description": "Python course",
                                        "level": 3,
                                        "duration": 470,
                                        "seasonId": 1,
                                        "instructorId": 1,
                                        "typeCourse": "MANDATORY",
                                        "departmentId": 1
                                    }
                                ],
                                "timestamp": "2025-10-18T17:47:30.675114114"
                            }
                                """
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista sin contenido", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno en el servidor", content = @Content)
        }
    )
    @GetMapping("/mandatory")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getMandatorysByDepartament(@RequestParam(required = true) Long departmentId) {
        loggingService.logInfo("Obteniendo Courses obligatorios para departmentId: {}", departmentId);
        try {
            List<Course> courses = courseService.findByStatusMandatory(departmentId);

            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses obligatorios para departmentId: {}", departmentId);
                return ResponseEntity.noContent().build();
            }

            List<CourseResponse> courseResponses = courses.stream()
                    .map(courseWebMapper::domainToResponse)
                    .collect(Collectors.toList());
            loggingService.logInfo("Se encontraron {} Courses obligatorios para departmentId: {}", courseResponses.size(), departmentId);
            return ResponseEntity.ok(ApiResponse.success("Cursos obligatorios obtenidos por temporada: " + departmentId, courseResponses));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Courses obligatorios para departmentId {}: {}", departmentId, e.getMessage(), e);
            throw e;
        }
    }

    @Operation(
        summary = "Eliminar un curso",
        description = "Elimina el registro de un curso según su ID.",
        parameters = {
            @Parameter(
                name = "id",
                description = "Identificador del curso a eliminar",
                required = true,
                example = "1"
            )
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Curso eliminado correctamente", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Curso no encontrado", content = @Content),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> deleteCourse(@PathVariable Long id) {
        loggingService.logInfo("Iniciando eliminación de Course con ID: {}", id);
        try {
            Course course = courseService.deleteCourse(id).get();
            CourseResponse courseResponse = courseWebMapper.domainToResponse(course);
            loggingService.logInfo("Course ID {} eliminado exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Curso eliminado correctamente", courseResponse));
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Course ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // Método auxiliar para truncar títulos de cursos en los logs
    private String truncateTitle(String title) {
        if (title == null) {
            return "null";
        }
        return title.length() > 30 ? title.substring(0, 30) + "..." : title;
    }
}