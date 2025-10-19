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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CoursesController {

    private final ICourseService courseService;
    private final CourseWebMapper courseWebMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@PathVariable Long id) {
        loggingService.logInfo("Obteniendo Course con ID: {}", id);
        try {
            Course course = courseService.getCourseById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Course con ID " + id + " no encontrado"));
            CourseResponse courseResponse = courseWebMapper.domainToResponse(course);
            loggingService.logInfo("Course ID {} obtenido exitosamente", id);
            return ResponseEntity.ok(ApiResponse.success("Curso obtenido correctamente", courseResponse));
        } catch (Exception e) {
            loggingService.logError("Error al obtener Course ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> deleteCourse(@PathVariable Long id) {
        loggingService.logInfo("Iniciando eliminación de Course con ID: {}", id);
        try {
            Course course = courseService.deleteCourse(id)
                    .orElseThrow(() -> new IllegalArgumentException("Course con ID " + id + " no encontrado"));
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