package com.desarrollox.learncompany.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.DepartmentIncorrectException;
import com.desarrollox.learncompany.domain.exception.InscriptionAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.InscriptionNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.domain.service.impl.InscriptionService;

@ExtendWith(MockitoExtension.class)
public class InscriptionServiceTest {
    
    @Mock
    private IRepositoryInscription repositoryInscription;

    @Mock
    private IRepositoryUser repositoryUser;

    @Mock
    private IRepositoryCourse repositoryCourse;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private InscriptionService inscriptionService;

    private Inscription inscription;
    private Employee employee;
    private Course course;
    private Department department;
    private List<Inscription> inscriptionList;

    @BeforeEach
    void setUp(){

        department = new Department();
        department.setId(1L);
        department.setHierarchy(2);

        employee = new Employee();
        employee.setId(1L);
        employee.setRole(Role.EMPLOYEE);
        employee.setDepartment(department);

        course = new Course();
        course.setId(1L);
        course.setDuration(488);
        course.setDepartment(department);

        inscription = new Inscription();
        inscription.setId(1L);
        inscription.setEmployee(employee);
        inscription.setCourse(course);

        inscriptionList = new ArrayList<>();
        inscriptionList.add(inscription);
    }

    @Test //201
    void createInscription_success(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryInscription.findAll()).thenReturn(new ArrayList<>());
        when(repositoryCourse.findById(anyLong())).thenReturn(Optional.of(course));
        when(repositoryInscription.save(any(Inscription.class))).thenReturn(inscription);

        Inscription result = inscriptionService.createInscription(inscription);

        assertNotNull(result);
        assertEquals(inscription, result);
        assertEquals(1L, result.getId());

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, times(3)).findById(anyLong());
        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryInscription).findAll();
        verify(repositoryCourse, times(2)).findById(anyLong());
        verify(repositoryInscription).save(any(Inscription.class));
    }

    @Test //404
    void createInscription_UserNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class, 
            () -> inscriptionService.createInscription(inscription)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, never()).findById(anyLong());
        verify(repositoryCourse, never()).existsById(anyLong());
        verify(repositoryInscription, never()).findAll();
        verify(repositoryCourse, never()).findById(anyLong());
        verify(repositoryInscription, never()).save(any(Inscription.class));
    }

    @Test //409
    void createInscription_InvalidRole(){
        Employee employeeLocal = new Employee();
        employeeLocal.setId(2L);
        employeeLocal.setRole(Role.INSTRUCTOR);

        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employeeLocal));

        InvalidRoleException thrown = assertThrows(
            InvalidRoleException.class, 
            () -> inscriptionService.createInscription(inscription)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
        verify(repositoryCourse, never()).existsById(anyLong());
        verify(repositoryInscription, never()).findAll();
        verify(repositoryCourse, never()).findById(anyLong());
        verify(repositoryInscription, never()).save(any(Inscription.class));
    }

    @Test //404
    void createInscription_CourseNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repositoryCourse.existsById(anyLong())).thenReturn(false);

        CourseNotFoundException thrown = assertThrows(
            CourseNotFoundException.class, 
            () -> inscriptionService.createInscription(inscription)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryInscription, never()).findAll();
        verify(repositoryCourse, never()).findById(anyLong());
        verify(repositoryInscription, never()).save(any(Inscription.class));
    }

    @Test //403
    void createInscription_DepartmentIncorrect(){

        Department departmentLocal = new Department();
        departmentLocal.setId(3L);
        departmentLocal.setHierarchy(3);

        Employee employeeLocal = new Employee();
        employeeLocal.setId(3L);
        employeeLocal.setDepartment(departmentLocal);
        employeeLocal.setRole(Role.EMPLOYEE);

        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employeeLocal));
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.findById(anyLong())).thenReturn(Optional.of(course));

        DepartmentIncorrectException thrown = assertThrows(
            DepartmentIncorrectException.class, 
            () -> inscriptionService.createInscription(inscription)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, times(2)).findById(anyLong());
        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryCourse).findById(anyLong());
        verify(repositoryInscription, never()).findAll();
        verify(repositoryInscription, never()).save(any(Inscription.class));

    }

    @Test //409
    void createInscription_InscriptionAlreadyRegistered(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.findById(anyLong())).thenReturn(Optional.of(course));
        when(repositoryInscription.findAll()).thenReturn(inscriptionList);

        InscriptionAlreadyRegisteredException thrown = assertThrows(
            InscriptionAlreadyRegisteredException.class, 
            () -> inscriptionService.createInscription(inscription)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, times(2)).findById(anyLong());
        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryCourse).findById(anyLong());
        verify(repositoryInscription).findAll();
        verify(repositoryInscription, never()).save(any(Inscription.class));
    }

    @Test //200
    void getInscriptionById_success(){
        when(repositoryInscription.existsById(anyLong())).thenReturn(true);
        when(repositoryInscription.findById(anyLong())).thenReturn(Optional.of(inscription));

        Optional<Inscription> result = inscriptionService.getInscriptionById(1L);

        assertTrue(result.isPresent());
        assertEquals(inscription, result.get());
        assertEquals(inscription.getId(), result.get().getId());

        verify(repositoryInscription).existsById(anyLong());
        verify(repositoryInscription).findById(anyLong());
    }

    @Test //404
    void getInscriptionById_InscriptionNotFound(){
        when(repositoryInscription.existsById(anyLong())).thenReturn(false);

        InscriptionNotFoundException thrown = assertThrows(
            InscriptionNotFoundException.class, 
            () -> inscriptionService.getInscriptionById(1L)
        );
        
        assertNotNull(thrown);

        verify(repositoryInscription).existsById(anyLong());
        verify(repositoryInscription, never()).findById(anyLong());
    }

    @Test //200
    void deleteInscription_success(){
        when(repositoryInscription.existsById(anyLong())).thenReturn(true);
        when(repositoryInscription.delete(anyLong())).thenReturn(Optional.of(inscription));

        Optional<Inscription> result = inscriptionService.deleteInscription(1L);

        assertNotNull(result);
        assertEquals(1L, result.get().getId());

        verify(repositoryInscription).existsById(anyLong());
        verify(repositoryInscription).delete(anyLong());
    }

    @Test //404
    void deleteInscription_InscriptionNotFound(){
        when(repositoryInscription.existsById(anyLong())).thenReturn(false);

        InscriptionNotFoundException thrown = assertThrows(
            InscriptionNotFoundException.class, 
            () -> inscriptionService.deleteInscription(1L)
        );

        assertNotNull(thrown);

        verify(repositoryInscription).existsById(anyLong());
        verify(repositoryInscription, never()).delete(anyLong());
    }

    @Test //200
    void findByEmployeeId_success(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryInscription.findInscriptionsByEmployeeId(anyLong())).thenReturn(inscriptionList);

        List<Inscription> result = inscriptionService.findByEmployeeId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(inscriptionList, result);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryInscription).findInscriptionsByEmployeeId(anyLong());
    }

    @Test //404
    void findByEmployeeId_UserNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class, 
            () -> inscriptionService.findByEmployeeId(1L)
        );
        
        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryInscription, never()).findInscriptionsByEmployeeId(anyLong());
    }

    @Test //204
    void findByEmployeeId_isEmpty(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryInscription.findInscriptionsByEmployeeId(anyLong())).thenReturn(new ArrayList<>());

        List<Inscription> result = inscriptionService.findByEmployeeId(1L);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(inscriptionList, result);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryInscription).findInscriptionsByEmployeeId(anyLong());
    }

    @Test //200
    void findByCourseId_success(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryInscription.findInscriptionsByCourseId(anyLong())).thenReturn(inscriptionList);

        List<Inscription> result = inscriptionService.findByCourseId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(inscriptionList, result);

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryInscription).findInscriptionsByCourseId(anyLong());
    }

    @Test //404
    void findByCourseId_CourseNotFound(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(false);

        CourseNotFoundException thrown = assertThrows(
            CourseNotFoundException.class, 
            () -> inscriptionService.findByCourseId(1L)
        );
        
        assertNotNull(thrown);

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryInscription, never()).findInscriptionsByCourseId(anyLong());
    }

    @Test //204
    void findByCourseId_isEmpty(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryInscription.findInscriptionsByCourseId(anyLong())).thenReturn(new ArrayList<>());

        List<Inscription> result = inscriptionService.findByCourseId(1L);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(inscriptionList, result);

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryInscription).findInscriptionsByCourseId(anyLong());
    }
}