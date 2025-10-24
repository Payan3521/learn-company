package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.accessDb.IRepositorySeason;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.DepartmentNotFoundException;
import com.desarrollox.learncompany.domain.exception.DurationCourseInvalidException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.SeasonNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.service.ICourseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {

    private final IRepositoryCourse repositoryCourse;
    private final IRepositoryDepartment repositoryDepartment;
    private final IRepositorySeason repositorySeason;
    private final IRepositoryUser repositoryUser;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = false)
    @Override
    public Course createCourse(Course course) {
        loggingService.logInfo("Iniciando creación de Course con título: {}, departmentId: {}, seasonId: {}, instructorId: {}", 
                course.getTitle(), course.getDepartment().getId(), course.getSeason().getId(), course.getInstructor().getId());
        try {
            if (!repositoryDepartment.existsById(course.getDepartment().getId())) {
                loggingService.logError("Departamento con ID {} no encontrado", course.getDepartment().getId());
                throw new DepartmentNotFoundException(course.getDepartment().getId());
            }
            if (!repositorySeason.existsById(course.getSeason().getId())) {
                loggingService.logError("Temporada con ID {} no encontrada", course.getSeason().getId());
                throw new SeasonNotFoundException(course.getSeason().getId());
            }
            if (!repositoryUser.existsById(course.getInstructor().getId())) {
                loggingService.logError("Usuario con ID {} no encontrado", course.getInstructor().getId());
                throw new UserNotFoundException(course.getInstructor().getId());
            }
            if (!repositoryUser.findById(course.getInstructor().getId()).get().isInstructor()) {
                loggingService.logError("El usuario con ID {} no tiene rol INSTRUCTOR", course.getInstructor().getId());
                throw new InvalidRoleException("El usuario con ID " + course.getInstructor().getId() + " no tiene rol de INSTRUCTOR");
            }
            if (course.getDuration() > repositorySeason.findById(course.getSeason().getId()).get().getDuration()) {
                loggingService.logError("Duración del curso {} excede la duración de la temporada con ID {}", 
                        course.getDuration(), course.getSeason().getId());
                throw new DurationCourseInvalidException();
            }

            loggingService.logDebug("Obteniendo entidades para Course: departmentId={}, seasonId={}, instructorId={}", 
                    course.getDepartment().getId(), course.getSeason().getId(), course.getInstructor().getId());
            course.setDepartment(repositoryDepartment.findById(course.getDepartment().getId()).get());
            course.setSeason(repositorySeason.findById(course.getSeason().getId()).get());
            course.setInstructor((Instructor) repositoryUser.findById(course.getInstructor().getId()).get());

            Course savedCourse = repositoryCourse.save(course);
            loggingService.logInfo("Course creado exitosamente con ID: {} y título: {}", savedCourse.getId(), savedCourse.getTitle());
            return savedCourse;
        } catch (Exception e) {
            loggingService.logError("Error al crear Course con título {}: {}", course.getTitle(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Course> getCoursesBySeasonId(Long seasonId) {
        loggingService.logInfo("Obteniendo Courses para seasonId: {}", seasonId);
        try {
            if(!repositorySeason.existsById(seasonId)){
                loggingService.logError("Temporada con ID {} no encontrada", seasonId);
                throw new SeasonNotFoundException(seasonId);
            }
            List<Course> courses = repositoryCourse.getCoursesBySeasonId(seasonId);
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

    @Transactional(readOnly = true)
    @Override
    public Optional<Course> getCourseById(Long courseId) {
        loggingService.logInfo("Obteniendo Course con ID: {}", courseId);
        try {
            if (!repositoryCourse.existsById(courseId)) {
                loggingService.logError("Course con ID {} no encontrado", courseId);
                throw new CourseNotFoundException(courseId);
            }
            Optional<Course> course = repositoryCourse.findById(courseId);
            loggingService.logInfo("Course ID {} obtenido exitosamente", courseId);
            return course;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Course ID {}: {}", courseId, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Course> getAllCourses() {
        loggingService.logInfo("Obteniendo todos los Courses");
        try {
            List<Course> courses = repositoryCourse.findAll();
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

    @Transactional(readOnly = true)
    @Override
    public List<Course> findByDepartmentId(Long departmentId) {
        loggingService.logInfo("Obteniendo Courses para departmentId: {}", departmentId);
        try {
            if(!repositoryDepartment.existsById(departmentId)){
                loggingService.logError("Departamento con ID {} no encontrado", departmentId);
                throw new DepartmentNotFoundException(departmentId);
            }
            List<Course> courses = repositoryCourse.findByDepartmentId(departmentId);
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

    @Transactional(readOnly = true)
    @Override
    public List<Course> findByTitleContaining(String title) {
        loggingService.logInfo("Obteniendo Courses con título que contiene: {}", title);
        try {
            List<Course> courses = repositoryCourse.findByTitleContaining(title);
            if (courses.isEmpty()) {
                loggingService.logWarning("No se encontraron Courses con título que contiene: {}", title);
            } else {
                loggingService.logInfo("Se encontraron {} Courses con título que contiene: {}", courses.size(), title);
            }
            return courses;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Courses con título que contiene {}: {}", title, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Course> findByStatusOptional(Long departmentId) {
        loggingService.logInfo("Obteniendo Courses opcionales para departmentId: {}", departmentId);
        try {
            if(!repositoryDepartment.existsById(departmentId)){
                loggingService.logError("Departamento con ID {} no encontrado", departmentId);
                throw new DepartmentNotFoundException(departmentId);
            }
            List<Course> courses = repositoryCourse.findByStatusOptional(departmentId);
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

    @Transactional(readOnly = true)
    @Override
    public List<Course> findByStatusMandatory(Long departmentId) {
        loggingService.logInfo("Obteniendo Courses obligatorios para departmentId: {}", departmentId);
        try {
            if(!repositoryDepartment.existsById(departmentId)){
                loggingService.logError("Departamento con ID {} no encontrado", departmentId);
                throw new DepartmentNotFoundException(departmentId);
            }
            List<Course> courses = repositoryCourse.findByStatusMandatory(departmentId);
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

    @Transactional(readOnly = false)
    @Override
    public Optional<Course> deleteCourse(Long courseId) {
        loggingService.logInfo("Iniciando eliminación de Course con ID: {}", courseId);
        try {
            if (!repositoryCourse.existsById(courseId)) {
                loggingService.logError("Course con ID {} no encontrado", courseId);
                throw new CourseNotFoundException(courseId);
            }
            Optional<Course> deletedCourse = repositoryCourse.delete(courseId);
            loggingService.logInfo("Course ID {} eliminado exitosamente", courseId);
            return deletedCourse;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Course ID {}: {}", courseId, e.getMessage(), e);
            throw e;
        }
    }
}