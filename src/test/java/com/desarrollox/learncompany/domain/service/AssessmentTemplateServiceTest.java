package com.desarrollox.learncompany.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
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
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentTemplate;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryModule;
import com.desarrollox.learncompany.domain.exception.AssessmentTemplateNotFoundException;
import com.desarrollox.learncompany.domain.exception.ModuleNotFoundException;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.domain.model.FeedBack;
import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.domain.service.impl.AssessmentTemplateService;

@ExtendWith(MockitoExtension.class)
public class AssessmentTemplateServiceTest {
    
    @Mock
    private IRepositoryAssessmentTemplate repositoryAssessmentTemplate;

    @Mock
    private IRepositoryModule repositoryModule;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private AssessmentTemplateService assessmentTemplateService;

    private AssessmentTemplate assessmentTemplate;
    private Module module;
    private FeedBack feedBack;
    private List<FeedBack> listaFeedbacks;
    private List<AssessmentTemplate> lista;

    @BeforeEach
    void setUp(){
        module = new Module();
        module.setId(1L);
        
        assessmentTemplate = new AssessmentTemplate();
        assessmentTemplate.setId(1L);
        assessmentTemplate.setModule(module);

        lista = new ArrayList<>();
        lista.add(assessmentTemplate);

        feedBack = new FeedBack();
        feedBack.setId(1L);

        listaFeedbacks = new ArrayList<>();
        listaFeedbacks.add(feedBack);
    }

    @Test //200
    void getAssessmentsByModuleId_success(){
        when(repositoryModule.existsById(anyLong())).thenReturn(true);
        when(repositoryAssessmentTemplate.findAssessmentsByModuleId(anyLong())).thenReturn(lista);

        List<AssessmentTemplate> resultList = assessmentTemplateService.getAssessmentsByModuleId(1L);

        assertFalse(resultList.isEmpty());
        assertEquals(1, resultList.size());
        assertEquals(lista, resultList);

        verify(repositoryModule).existsById(anyLong());
        verify(repositoryAssessmentTemplate).findAssessmentsByModuleId(anyLong());
    }

    @Test //404
    void getAssessmentsByModuleId_ModuleNotFound(){
        when(repositoryModule.existsById(anyLong())).thenReturn(false);
        
        ModuleNotFoundException thrown = assertThrows(
            ModuleNotFoundException.class, 
            () -> assessmentTemplateService.getAssessmentsByModuleId(1L)    
        );

       assertNotNull(thrown);
       verify(repositoryModule).existsById(anyLong());
       verify(repositoryAssessmentTemplate, never()).findAssessmentsByModuleId(anyLong());
    }

    @Test //200
    void findById_success(){
        when(repositoryAssessmentTemplate.existsById(anyLong())).thenReturn(true);
        when(repositoryAssessmentTemplate.findById(anyLong())).thenReturn(Optional.of(assessmentTemplate));

        Optional<AssessmentTemplate> result = assessmentTemplateService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(assessmentTemplate, result.get());
        assertEquals(assessmentTemplate.getId(), result.get().getId());

        verify(repositoryAssessmentTemplate).existsById(anyLong());
        verify(repositoryAssessmentTemplate).findById(anyLong());
    }

    @Test //404
    void findById_AssessmentTemplateNotFound(){
        when(repositoryAssessmentTemplate.existsById(anyLong())).thenReturn(false);

        AssessmentTemplateNotFoundException thrown = assertThrows(
            AssessmentTemplateNotFoundException.class,
            () -> assessmentTemplateService.findById(1l)
        );

        assertNotNull(thrown);
        verify(repositoryAssessmentTemplate).existsById(anyLong());
        verify(repositoryAssessmentTemplate, never()).findById(anyLong());
    }

    @Test //200
    void getFeedbackById_success(){
        when(repositoryAssessmentTemplate.existsById(anyLong())).thenReturn(true);
        when(repositoryAssessmentTemplate.getFeedback(anyLong())).thenReturn(listaFeedbacks);
        List<FeedBack> result = assessmentTemplateService.getFeedbackById(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(listaFeedbacks, result);

        verify(repositoryAssessmentTemplate).existsById(anyLong());
        verify(repositoryAssessmentTemplate).getFeedback(anyLong());
    }

    @Test //204
    void getFeedbackById_isEmpty(){
        when(repositoryAssessmentTemplate.existsById(anyLong())).thenReturn(true);
        when(repositoryAssessmentTemplate.getFeedback(anyLong())).thenReturn(new ArrayList<>());
        List<FeedBack> result = assessmentTemplateService.getFeedbackById(1L);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(listaFeedbacks, result);

        verify(repositoryAssessmentTemplate).existsById(anyLong());
        verify(repositoryAssessmentTemplate).getFeedback(anyLong());
    }

    @Test
    void getFeedbackById_AssessmentTemplateNotFound(){
        when(repositoryAssessmentTemplate.existsById(anyLong())).thenReturn(false);

        AssessmentTemplateNotFoundException thrown = assertThrows(
            AssessmentTemplateNotFoundException.class, 
            () -> assessmentTemplateService.getFeedbackById(1L)
        );

        assertNotNull(thrown);

        verify(repositoryAssessmentTemplate).existsById(anyLong());
        verify(repositoryAssessmentTemplate, never()).getFeedback(anyLong());
    }

}