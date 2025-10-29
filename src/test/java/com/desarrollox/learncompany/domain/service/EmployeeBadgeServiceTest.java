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
import com.desarrollox.learncompany.domain.accessDb.IRepositoryBadge;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryEmployeeBadge;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.BadgeNotFoundException;
import com.desarrollox.learncompany.domain.exception.EmployeeBadgeAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.EmployeeBadgeNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.EmployeeBadge;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.domain.service.impl.EmployeeBadgeService;

@ExtendWith(MockitoExtension.class)
class EmployeeBadgeServiceTest {
    
    @Mock
    private IRepositoryBadge repositoryBadge;

    @Mock
    private IRepositoryUser repositoryUser;

    @Mock
    private IRepositoryEmployeeBadge repositoryEmployeeBadge;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private EmployeeBadgeService employeeBadgeService;

    private EmployeeBadge employeeBadge;
    private Employee employee;
    private Badge badge;
    private List<EmployeeBadge> employeeBadgeList;

    @BeforeEach
    void setUp(){
        employee = new Employee();
        employee.setId(1L);
        employee.setRole(Role.EMPLOYEE);
        
        badge = new Badge();
        badge.setId(1L);
        badge.setName("Maestro");

        employeeBadge = new EmployeeBadge();
        employeeBadge.setId(1L);
        employeeBadge.setBadge(badge);
        employeeBadge.setEmployee(employee);

        employeeBadgeList = new ArrayList<>();
        employeeBadgeList.add(employeeBadge);
    }

    @Test //201
    void assignBadgeToEmployee_success(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repositoryBadge.existsById(anyLong())).thenReturn(true);
        when(repositoryEmployeeBadge.findAll()).thenReturn(new ArrayList<>());
        when(repositoryBadge.findById(anyLong())).thenReturn(Optional.of(badge));
        when(repositoryEmployeeBadge.save(any(EmployeeBadge.class))).thenReturn(employeeBadge);

        EmployeeBadge result = employeeBadgeService.assignBadgeToEmployee(employeeBadge);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, times(2)).findById(anyLong());
        verify(repositoryBadge).existsById(anyLong());
        verify(repositoryBadge).findById(anyLong());
        verify(repositoryEmployeeBadge).findAll();
        verify(repositoryEmployeeBadge).save(any(EmployeeBadge.class));
    }

    @Test //404
    void assignBadgeToEmployee_UserNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class, 
            () -> employeeBadgeService.assignBadgeToEmployee(employeeBadge)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, never()).findById(anyLong());
        verify(repositoryBadge, never()).existsById(anyLong());
        verify(repositoryBadge, never()).findById(anyLong());
        verify(repositoryEmployeeBadge, never()).findAll();
        verify(repositoryEmployeeBadge, never()).save(any(EmployeeBadge.class));
    }

    @Test //409
    void assignBadgeToEmployee_InvalidRole(){

        Employee employeeLocal = new Employee();
        employeeLocal.setId(2L);
        employeeLocal.setRole(Role.INSTRUCTOR);

        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employeeLocal));

        InvalidRoleException thrown = assertThrows(
            InvalidRoleException.class, 
            () -> employeeBadgeService.assignBadgeToEmployee(employeeBadge)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
        verify(repositoryBadge, never()).existsById(anyLong());
        verify(repositoryBadge, never()).findById(anyLong());
        verify(repositoryEmployeeBadge, never()).findAll();
        verify(repositoryEmployeeBadge, never()).save(any(EmployeeBadge.class));
    }

    @Test //409
    void assignBadgeToEmployee_EmployeeBadgeAlreadyRegistered(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repositoryBadge.existsById(anyLong())).thenReturn(true);
        when(repositoryEmployeeBadge.findAll()).thenReturn(employeeBadgeList);

        EmployeeBadgeAlreadyRegisteredException thrown = assertThrows(
            EmployeeBadgeAlreadyRegisteredException.class, 
            () -> employeeBadgeService.assignBadgeToEmployee(employeeBadge)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
        verify(repositoryBadge).existsById(anyLong());
        verify(repositoryBadge, never()).findById(anyLong());
        verify(repositoryEmployeeBadge).findAll();
        verify(repositoryEmployeeBadge, never()).save(any(EmployeeBadge.class));
    }

    @Test //404
    void assignBadgeToEmployee_BadgeNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repositoryBadge.existsById(anyLong())).thenReturn(false);

        BadgeNotFoundException thrown = assertThrows(
            BadgeNotFoundException.class, 
            () -> employeeBadgeService.assignBadgeToEmployee(employeeBadge)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
        verify(repositoryBadge).existsById(anyLong());
        verify(repositoryBadge, never()).findById(anyLong());
        verify(repositoryEmployeeBadge, never()).findAll();
        verify(repositoryEmployeeBadge, never()).save(any(EmployeeBadge.class));
    }

    @Test //200
    void findById_success(){
        when(repositoryEmployeeBadge.existsById(anyLong())).thenReturn(true);
        when(repositoryEmployeeBadge.findById(anyLong())).thenReturn(Optional.of(employeeBadge));

        Optional<EmployeeBadge> result = employeeBadgeService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(employeeBadge, result.get());
        assertEquals(employeeBadge.getId(), result.get().getId());

        verify(repositoryEmployeeBadge).existsById(anyLong());
        verify(repositoryEmployeeBadge).findById(anyLong());
    }

    @Test //404
    void findById_EmployeeBadgeNotFound(){
        when(repositoryEmployeeBadge.existsById(anyLong())).thenReturn(false);

        EmployeeBadgeNotFoundException thrown = assertThrows(
            EmployeeBadgeNotFoundException.class, 
            () -> employeeBadgeService.findById(1L)
        );

        assertNotNull(thrown);

        verify(repositoryEmployeeBadge).existsById(anyLong());
        verify(repositoryEmployeeBadge, never()).findById(anyLong());
    }

    @Test //200
    void findAll_success(){
        when(repositoryEmployeeBadge.findAll()).thenReturn(employeeBadgeList);

        List<EmployeeBadge> result = employeeBadgeService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(employeeBadgeList, result);

        verify(repositoryEmployeeBadge).findAll();
    }

    @Test //204
    void findAll_isEmpty(){
        when(repositoryEmployeeBadge.findAll()).thenReturn(new ArrayList<>());

        List<EmployeeBadge> result = employeeBadgeService.findAll();

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(employeeBadgeList, result);

        verify(repositoryEmployeeBadge).findAll();
    }

    @Test //200
    void delete_success(){
        when(repositoryEmployeeBadge.existsById(anyLong())).thenReturn(true);
        when(repositoryEmployeeBadge.delete(anyLong())).thenReturn(Optional.of(employeeBadge));

        Optional<EmployeeBadge> result = employeeBadgeService.delete(1L);

        assertNotNull(result);
        assertEquals(1L, result.get().getId());

        verify(repositoryEmployeeBadge).existsById(anyLong());
        verify(repositoryEmployeeBadge).delete(anyLong());
    }

    @Test //404
    void delete_EmployeeBadgeNotFound(){
        when(repositoryEmployeeBadge.existsById(anyLong())).thenReturn(false);

        EmployeeBadgeNotFoundException thrown = assertThrows(
            EmployeeBadgeNotFoundException.class, 
            () -> employeeBadgeService.delete(1L)
        );

        assertNotNull(thrown);

        verify(repositoryEmployeeBadge).existsById(anyLong());
        verify(repositoryEmployeeBadge, never()).delete(anyLong());
    }

}