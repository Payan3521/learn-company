package com.desarrollox.learncompany.domain.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryIncription;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.model.Statistic;
import com.desarrollox.learncompany.domain.service.IStatisticService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {

    private final IRepositoryCourse repositoryCourse;
    private final IRepositoryIncription repositoryIncription;

    @Transactional(readOnly = true)
    @Override
    public Optional<Statistic> getStatistic() {
        // Obtener todos los cursos
        List<Course> courses = repositoryCourse.findAll();

        // Si no hay cursos, retornar vacío
        if (courses == null || courses.isEmpty()) {
            return Optional.empty();
        }

        for (Course course : courses) {
            course.setInscriptions(repositoryIncription.findInscriptionsByCourseId(course.getId()));
        }
        
        // Encontrar el curso con más inscripciones
        Course courseTop = courses.stream()
            .max(Comparator.comparingInt(Course::getTotalInscriptions))
            .orElse(null);
        
        // Encontrar el curso con menos inscripciones
        Course courseLess = courses.stream()
            .min(Comparator.comparingInt(Course::getTotalInscriptions))
            .orElse(null);
        
        // Si no encontramos cursos, retornar vacío
        if (courseTop == null || courseLess == null) {
            return Optional.empty();
        }
        
        // Crear y retornar la estadística
        Statistic statistic = new Statistic(courseTop, courseLess);
        return Optional.of(statistic);
    }
}
