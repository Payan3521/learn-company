package com.desarrollox.learncompany.domain.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryInscription;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.model.Statistic;
import com.desarrollox.learncompany.domain.service.IStatisticService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {

    private final IRepositoryCourse repositoryCourse;
    private final IRepositoryInscription repositoryIncription;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = true)
    @Override
    public Optional<Statistic> getStatistic() {
        loggingService.logInfo("Iniciando obtención de Statistic");
        try {
            // Obtener todos los cursos
            loggingService.logDebug("Obteniendo todos los Courses");
            List<Course> courses = repositoryCourse.findAll();

            // Si no hay cursos, retornar vacío
            if (courses == null || courses.isEmpty()) {
                loggingService.logError("No existen cursos disponibles");
                throw new CourseNotFoundException("No existen cursos disponibles");
            }
            loggingService.logInfo("Se encontraron {} Courses", courses.size());

            // Asignar inscripciones a cada curso
            loggingService.logDebug("Obteniendo inscripciones para cada Course");
            for (Course course : courses) {
                course.setInscriptions(repositoryIncription.findInscriptionsByCourseId(course.getId()));
                loggingService.logDebug("Curso ID: {} tiene {} inscripciones", 
                        course.getId(), course.getTotalInscriptions());
            }

            // Encontrar el curso con más inscripciones
            loggingService.logDebug("Buscando curso con más inscripciones");
            Course courseTop = courses.stream()
                    .max(Comparator.comparingInt(Course::getTotalInscriptions))
                    .orElse(null);

            // Encontrar el curso con menos inscripciones
            loggingService.logDebug("Buscando curso con menos inscripciones");
            Course courseLess = courses.stream()
                    .min(Comparator.comparingInt(Course::getTotalInscriptions))
                    .orElse(null);

            // Si no encontramos cursos o todos tienen 0 inscripciones
            boolean allCoursesEmpty = courses.stream()
                    .allMatch(course -> course.getTotalInscriptions() == 0);

            if (courseTop == null || courseLess == null || allCoursesEmpty) {
                loggingService.logWarning("No se encontraron cursos válidos para generar Statistic");
                return Optional.empty();
            }

            // Crear y retornar la estadística
            loggingService.logInfo("Creando Statistic con courseTop ID: {} (inscripciones: {}) y courseLess ID: {} (inscripciones: {})", 
                    courseTop.getId(), courseTop.getTotalInscriptions(), courseLess.getId(), courseLess.getTotalInscriptions());
            Statistic statistic = new Statistic(courseTop, courseLess);
            loggingService.logInfo("Statistic generada exitosamente");
            return Optional.of(statistic);
        } catch (Exception e) {
            loggingService.logError("Error al generar Statistic: {}", e.getMessage(), e);
            throw e;
        }
    }
}