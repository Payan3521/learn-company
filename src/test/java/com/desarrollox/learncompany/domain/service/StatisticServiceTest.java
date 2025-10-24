package com.desarrollox.learncompany.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryInscription;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.domain.model.Statistic;
import com.desarrollox.learncompany.domain.service.impl.StatisticService;

@ExtendWith(MockitoExtension.class)
public class StatisticServiceTest {
    
    @Mock
    private IRepositoryCourse repositoryCourse;

    @Mock
    private IRepositoryInscription repositoryInscription;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private StatisticService statisticService;

    private Statistic statistic;
    private Course courseTop;
    private Course courseLess;
    private List<Course> courseList;

    @BeforeEach
    void setUp(){
        courseTop = new Course();
        courseTop.setId(1L);
        courseTop.setTitle("java");

        courseLess = new Course();
        courseLess.setId(2L);
        courseLess.setTitle("python");

        statistic = new Statistic();
        statistic.setId(1L);
        statistic.setNameCourseLess(courseLess);
        statistic.setNameCourseTop(courseTop);

        courseList = new ArrayList<>();
        courseList.add(courseLess);
        courseList.add(courseTop);
    }

    @Test //200
    void getStatistic_success() {
        when(repositoryCourse.findAll()).thenReturn(courseList);
        when(repositoryInscription.findInscriptionsByCourseId(anyLong()))
                .thenReturn(List.of(new Inscription(), new Inscription(), new Inscription())); 
        when(repositoryInscription.findInscriptionsByCourseId(anyLong()))
                .thenReturn(List.of(new Inscription()));

        Optional<Statistic> result = statisticService.getStatistic();

        assertTrue(result.isPresent());
        Statistic statistic = result.get();

        assertEquals(courseTop.getId(), statistic.getCourseTop().getId());
        assertEquals(courseLess.getId(), statistic.getCourseLess().getId());

        verify(repositoryCourse).findAll();
        verify(repositoryInscription, times(2)).findInscriptionsByCourseId(anyLong());
    }

    @Test //404
    void getStatistic_CourseNotFound(){
        when(repositoryCourse.findAll()).thenReturn(new ArrayList<>());

        CourseNotFoundException thrown = assertThrows(
            CourseNotFoundException.class, 
            () -> statisticService.getStatistic()
        );

        assertNotNull(thrown);

        verify(repositoryCourse).findAll();
        verify(repositoryInscription, never()).findInscriptionsByCourseId(anyLong());
    }

    @Test //204
    void getStatistic_Empty(){
        when(repositoryCourse.findAll()).thenReturn(courseList);
        when(repositoryInscription.findInscriptionsByCourseId(anyLong()))
                .thenReturn(new ArrayList<>()); 

        Optional<Statistic> result = statisticService.getStatistic();

        assertTrue(result.isEmpty());
    
        verify(repositoryCourse).findAll();
        verify(repositoryInscription, times(2)).findInscriptionsByCourseId(anyLong());
    }
}
