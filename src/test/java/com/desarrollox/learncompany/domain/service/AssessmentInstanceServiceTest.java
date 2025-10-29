package com.desarrollox.learncompany.domain.service;

import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentInstance;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryAssessmentTemplate;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.*;
import com.desarrollox.learncompany.domain.model.*;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.domain.service.impl.AssessmentInstanceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssessmentInstanceServiceTest {

    @Mock
    private IRepositoryAssessmentInstance repositoryAssessmentInstance;

    @Mock
    private IRepositoryAssessmentTemplate repositoryAssessmentTemplate;

    @Mock
    private IRepositoryUser repositoryUser;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private AssessmentInstanceService assessmentInstanceService;

    private AssessmentInstance assessmentInstance;
    private AssessmentTemplate assessmentTemplate;
    private Employee employee;

    @BeforeEach
    void setUp(){
        employee = new Employee();
        employee.setId(1L);
        employee.setRole(Role.EMPLOYEE);
        assessmentTemplate = new AssessmentTemplate();
        assessmentTemplate.setId(1L);
        assessmentInstance = new AssessmentInstance();
        assessmentInstance.setId(1L);
        assessmentInstance.setAssessmentTemplate(assessmentTemplate);
        assessmentInstance.setEmployee(employee);
        assessmentInstance.setGrade(3.0);
    }

    @Test //201
    void createAssessmentInstance_success(){
        
        when(repositoryAssessmentTemplate.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repositoryAssessmentTemplate.findById(anyLong())).thenReturn(Optional.of(assessmentTemplate));
        when(repositoryAssessmentInstance.createAssessmentInstance(any(AssessmentInstance.class)))
                .thenReturn(assessmentInstance);

        AssessmentInstance result = assessmentInstanceService.createAssessmentInstance(assessmentInstance);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        
        verify(repositoryAssessmentTemplate).existsById(anyLong());
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, times(2)).findById(anyLong());
        verify(repositoryAssessmentTemplate).findById(anyLong());
        verify(repositoryAssessmentInstance).createAssessmentInstance(any(AssessmentInstance.class));
    }

    @Test //404
    void createAssessmentInstance_AssessmentTemplateNotFound(){
        when(repositoryAssessmentTemplate.existsById(anyLong())).thenReturn(false);

        AssessmentTemplateNotFoundException thrown = assertThrows(
            AssessmentTemplateNotFoundException.class,
            () -> assessmentInstanceService.createAssessmentInstance(assessmentInstance)
        );

        assertNotNull(thrown);
        verify(repositoryAssessmentTemplate).existsById(anyLong());
        verify(repositoryAssessmentInstance, never()).createAssessmentInstance(any(AssessmentInstance.class));
    }

    @Test //404
    void createAssessmentInstance_UserNotFound(){
        when(repositoryAssessmentTemplate.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.existsById(anyLong())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class,
            () -> assessmentInstanceService.createAssessmentInstance(assessmentInstance)
        );

        assertNotNull(thrown);
        verify(repositoryAssessmentTemplate).existsById(anyLong());
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryAssessmentInstance, never()).createAssessmentInstance(any(AssessmentInstance.class));
    }

    @Test //409
    void createAssessmentInstance_InvalidRole(){

        Employee employeeLocal = new Employee();
        employeeLocal.setId(2L);
        employeeLocal.setRole(Role.INSTRUCTOR);

        when(repositoryAssessmentTemplate.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employeeLocal));

        InvalidRoleException thrown = assertThrows(
            InvalidRoleException.class,
            () -> assessmentInstanceService.createAssessmentInstance(assessmentInstance)
        );

        assertNotNull(thrown);
        verify(repositoryAssessmentTemplate).existsById(anyLong());
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
        verify(repositoryAssessmentInstance, never()).createAssessmentInstance(any(AssessmentInstance.class));
    }

    @Test //200
    void getAssessmentInstanceById_success(){
        when(repositoryAssessmentInstance.existsById(anyLong())).thenReturn(true);
        when(repositoryAssessmentInstance.getAssessmentInstanceById(anyLong())).thenReturn(Optional.of(assessmentInstance));

        Optional<AssessmentInstance> result = assessmentInstanceService.getAssessmentInstanceById(1L);

        assertTrue(result.isPresent());
        assertEquals(assessmentInstance, result.get());
        assertEquals(assessmentInstance.getId(), result.get().getId());

        verify(repositoryAssessmentInstance).existsById(anyLong());
        verify(repositoryAssessmentInstance).getAssessmentInstanceById(anyLong());
    }

    @Test //404
    void getAssessmentInstanceById_AssessmentInstanceNotFound(){
        when(repositoryAssessmentInstance.existsById(anyLong())).thenReturn(false);

        AssessmentInstanceNotFoundException thrown = assertThrows(
            AssessmentInstanceNotFoundException.class,
            () -> assessmentInstanceService.getAssessmentInstanceById(1l)
        );

        assertNotNull(thrown);
        verify(repositoryAssessmentInstance).existsById(anyLong());
        verify(repositoryAssessmentInstance, never()).getAssessmentInstanceById(anyLong());
    }

    @Test //200
    void getGrade_success(){
        AssessmentInstance assessmentLocal = new AssessmentInstance();
        assessmentLocal.setId(1L);
        assessmentLocal.setGrade(4.5);
        
        when(repositoryAssessmentInstance.getAssessmentInstanceById(anyLong())).thenReturn(Optional.of(assessmentLocal));

        Double grade = assessmentInstanceService.getGrade(1L);

        assertNotNull(grade);
        assertEquals(grade, assessmentLocal.getGrade());

        verify(repositoryAssessmentInstance).getAssessmentInstanceById(anyLong());
    }

    @Test //404
    void getGrade_AssessmentInstanceNotFound(){
        when(repositoryAssessmentInstance.getAssessmentInstanceById(anyLong())).thenReturn(Optional.empty());

        AssessmentInstanceNotFoundException thrown = assertThrows(
            AssessmentInstanceNotFoundException.class,
            () -> assessmentInstanceService.getGrade(1l)
        );

        assertNotNull(thrown);
        verify(repositoryAssessmentInstance).getAssessmentInstanceById(anyLong());
    }        
    

    @Test //200
    void assignGrade_success(){
        when(repositoryAssessmentInstance.getAssessmentInstanceById(anyLong())).thenReturn(Optional.of(assessmentInstance));
        when(repositoryAssessmentInstance.assignGrade(anyLong(), anyDouble())).thenReturn(Optional.of(assessmentInstance));

        Optional<AssessmentInstance> result = assessmentInstanceService.assignGrade(1L, 3.0);

        assertTrue(result.isPresent());
        assertEquals(assessmentInstance, result.get());
        assertEquals(3.0, result.get().getGrade());

        verify(repositoryAssessmentInstance).getAssessmentInstanceById(anyLong());
        verify(repositoryAssessmentInstance).assignGrade(anyLong(), anyDouble());
    }

    @Test //404
    void assignGrade_AssessmentInstanceNotFound(){
        when(repositoryAssessmentInstance.getAssessmentInstanceById(anyLong())).thenReturn(Optional.empty());

        AssessmentInstanceNotFoundException thrown = assertThrows(
            AssessmentInstanceNotFoundException.class,
            () -> assessmentInstanceService.assignGrade(1l, 3.0)
        );

        assertNotNull(thrown);
        verify(repositoryAssessmentInstance).getAssessmentInstanceById(anyLong());
        verify(repositoryAssessmentInstance, never()).assignGrade(anyLong(), anyDouble());
    }

}