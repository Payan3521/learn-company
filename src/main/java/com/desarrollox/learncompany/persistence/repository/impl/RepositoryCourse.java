package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.persistence.mapper.CourseMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryCourse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryCourse implements IRepositoryCourse {

    private final JpaRepositoryCourse jpaRepositoryCourse;
    private final CourseMapper courseMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public Course save(Course course) {
        loggingService.logInfo("Iniciando guardado de Course con título: {}", truncateTitle(course.getTitle()));
        try {
            Course savedCourse = courseMapper.toDomain(
                    jpaRepositoryCourse.save(courseMapper.toEntity(course))
            );
            loggingService.logInfo("Course guardado exitosamente con ID: {} y título: {}", 
                    savedCourse.getId(), truncateTitle(savedCourse.getTitle()));
            return savedCourse;
        } catch (Exception e) {
            loggingService.logError("Error al guardar Course con título {}: {}", 
                    truncateTitle(course.getTitle()), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Course> getCoursesBySeasonId(Long seasonId) {
        loggingService.logInfo("Obteniendo Courses para seasonId: {}", seasonId);
        try {
            List<Course> courses = jpaRepositoryCourse.findBySeasonId(seasonId)
                    .stream()
                    .map(courseMapper::toDomain)
                    .collect(Collectors.toList());
            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses para seasonId: {}", seasonId);
            } else {
                loggingService.logInfo("Se encontraron {} Courses para seasonId: {}", courses.size(), seasonId);
            }
            return courses;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Courses para seasonId {}: {}", seasonId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Course> findById(Long courseId) {
        loggingService.logInfo("Obteniendo Course con ID: {}", courseId);
        try {
            Optional<Course> course = jpaRepositoryCourse.findById(courseId)
                    .map(courseMapper::toDomain);
            if (course.isPresent()) {
                loggingService.logInfo("Course ID {} obtenido exitosamente", courseId);
            } else {
                loggingService.logWarning("Course con ID {} no encontrado", courseId);
            }
            return course;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Course ID {}: {}", courseId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Course> findAll() {
        loggingService.logInfo("Obteniendo todos los Courses");
        try {
            List<Course> courses = jpaRepositoryCourse.findAll()
                    .stream()
                    .map(courseMapper::toDomain)
                    .collect(Collectors.toList());
            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses");
            } else {
                loggingService.logInfo("Se encontraron {} Courses", courses.size());
            }
            return courses;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Courses: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Course> findByDepartmentId(Long departmentId) {
        loggingService.logInfo("Obteniendo Courses para departmentId: {}", departmentId);
        try {
            List<Course> courses = jpaRepositoryCourse.findByDepartmentId(departmentId)
                    .stream()
                    .map(courseMapper::toDomain)
                    .collect(Collectors.toList());
            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses para departmentId: {}", departmentId);
            } else {
                loggingService.logInfo("Se encontraron {} Courses para departmentId: {}", courses.size(), departmentId);
            }
            return courses;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Courses para departmentId {}: {}", departmentId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Course> findByTitleContaining(String title) {
        loggingService.logInfo("Obteniendo Courses con título que contiene: {}", truncateTitle(title));
        try {
            List<Course> courses = jpaRepositoryCourse.findByTitleContaining(title)
                    .stream()
                    .map(courseMapper::toDomain)
                    .collect(Collectors.toList());
            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses con título que contiene: {}", truncateTitle(title));
            } else {
                loggingService.logInfo("Se encontraron {} Courses con título que contiene: {}", courses.size(), truncateTitle(title));
            }
            return courses;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Courses con título que contiene {}: {}", truncateTitle(title), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Course> findByStatusOptional(Long departmentId) {
        loggingService.logInfo("Obteniendo Courses opcionales para departmentId: {}", departmentId);
        try {
            List<Course> courses = jpaRepositoryCourse.findByStatusOptional(departmentId)
                    .stream()
                    .map(courseMapper::toDomain)
                    .collect(Collectors.toList());
            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses opcionales para departmentId: {}", departmentId);
            } else {
                loggingService.logInfo("Se encontraron {} Courses opcionales para departmentId: {}", courses.size(), departmentId);
            }
            return courses;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Courses opcionales para departmentId {}: {}", departmentId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Course> findByStatusMandatory(Long departmentId) {
        loggingService.logInfo("Obteniendo Courses obligatorios para departmentId: {}", departmentId);
        try {
            List<Course> courses = jpaRepositoryCourse.findByStatusMandatory(departmentId)
                    .stream()
                    .map(courseMapper::toDomain)
                    .collect(Collectors.toList());
            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses obligatorios para departmentId: {}", departmentId);
            } else {
                loggingService.logInfo("Se encontraron {} Courses obligatorios para departmentId: {}", courses.size(), departmentId);
            }
            return courses;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Courses obligatorios para departmentId {}: {}", departmentId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Course> delete(Long courseId) {
        loggingService.logInfo("Iniciando eliminación de Course con ID: {}", courseId);
        try {
            Optional<Course> course = jpaRepositoryCourse.findById(courseId)
                    .map(entity -> {
                        jpaRepositoryCourse.delete(entity);
                        return courseMapper.toDomain(entity);
                    });
            if (course.isPresent()) {
                loggingService.logInfo("Course ID {} eliminado exitosamente", courseId);
            } else {
                loggingService.logWarning("Course con ID {} no encontrado para eliminación", courseId);
            }
            return course;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Course ID {}: {}", courseId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de Course con ID: {}", id);
        try {
            boolean exists = jpaRepositoryCourse.existsById(id);
            loggingService.logDebug("Course con ID {} existe: {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de Course ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // Método auxiliar para truncar títulos largos en los logs
    private String truncateTitle(String title) {
        if (title == null) {
            return "null";
        }
        return title.length() > 30 ? title.substring(0, 30) + "..." : title;
    }
}