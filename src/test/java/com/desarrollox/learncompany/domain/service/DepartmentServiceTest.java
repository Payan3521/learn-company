package com.desarrollox.learncompany.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.exception.DepartmentAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.DepartmentNotFoundException;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.domain.service.impl.DepartmentService;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {
    
    @Mock
    private IRepositoryDepartment repositoryDepartment;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private DepartmentService departmentService;

    private Department department;
    private List<Department> departmentList;

    @BeforeEach
    void setUp(){
        department = new Department();
        department.setId(1L);
        department.setHierarchy(1);
        department.setName("gestion");

        departmentList = new ArrayList<>();
        departmentList.add(department);
    }

    @Test //201
    void createDepartment_success(){
        when(repositoryDepartment.existsByName(anyString())).thenReturn(false);
        when(repositoryDepartment.save(any(Department.class))).thenReturn(department);

        Department result = departmentService.createDepartment(department);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(repositoryDepartment).existsByName(anyString());
        verify(repositoryDepartment).save(any(Department.class));
    }

    @Test //409
    void createDepartment_DepartmentAlreadyRegistered(){
        when(repositoryDepartment.existsByName(anyString())).thenReturn(true);

        DepartmentAlreadyRegisteredException thrown = assertThrows(
            DepartmentAlreadyRegisteredException.class,
            () -> departmentService.createDepartment(department)
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsByName(anyString());
        verify(repositoryDepartment, never()).save(any(Department.class));
    }

    @Test // 200
    void updateDepartment_success(){
        
        Department departmentLocal = new Department();
        departmentLocal.setId(1L);
        departmentLocal.setHierarchy(2);
        departmentLocal.setName("contaduria");

        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryDepartment.existsByName(anyString())).thenReturn(false);
        when(repositoryDepartment.update(anyLong(), any(Department.class))).thenReturn(Optional.of(departmentLocal));

        Optional<Department> result = departmentService.updateDepartment(1L, departmentLocal);

        assertNotNull(result);
        assertEquals(1L, result.get().getId());
        assertNotEquals(department, result.get());

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryDepartment).existsByName(anyString());
        verify(repositoryDepartment).update(anyLong(), any(Department.class));
    }

    @Test //404
    void updateDepartment_DepartmentNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(false);

        DepartmentNotFoundException thrown = assertThrows(
            DepartmentNotFoundException.class, 
            () -> departmentService.updateDepartment(1L, department)
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryDepartment, never()).existsByName(anyString());
        verify(repositoryDepartment, never()).update(anyLong(), any(Department.class));
    }

    @Test //409
    void updateDepartment_DepartmentAlreadyRegistered(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryDepartment.existsByName(anyString())).thenReturn(true);

        DepartmentAlreadyRegisteredException thrown = assertThrows(
            DepartmentAlreadyRegisteredException.class, 
            () -> departmentService.updateDepartment(1L, department)
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryDepartment).existsByName(anyString());
        verify(repositoryDepartment, never()).update(anyLong(), any(Department.class));
    }

    @Test //200
    void getDepartmentById_success(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryDepartment.findById(anyLong())).thenReturn(Optional.of(department));

        Optional<Department> result = departmentService.getDepartmentById(1L);

        assertTrue(result.isPresent());
        assertEquals(department, result.get());
        assertEquals(department.getId(), result.get().getId());

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryDepartment).findById(anyLong());
    }

    @Test //404
    void getDepartmentById_DepartmentNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(false);

        DepartmentNotFoundException thrown = assertThrows(
            DepartmentNotFoundException.class,
            () -> departmentService.getDepartmentById(1L)
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryDepartment, never()).findById(anyLong());
    }

    @Test //200
    void deleteDepartment_success(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryDepartment.delete(anyLong())).thenReturn(Optional.of(department));

        Optional<Department> result = departmentService.deleteDepartment(1L);

        assertNotNull(result);
        assertEquals(1L, result.get().getId());
        assertEquals(department, result.get());

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryDepartment).delete(anyLong());
    }

    @Test //404
    void deleteDepartment_DepartmentNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(false);

        DepartmentNotFoundException thrown = assertThrows(
            DepartmentNotFoundException.class,
            () -> departmentService.deleteDepartment(1L)
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryDepartment, never()).delete(anyLong());
    }

    @Test //200
    void getAllDepartments_success(){
        when(repositoryDepartment.findAll()).thenReturn(departmentList);

        List<Department> result = departmentService.getAllDepartments();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(departmentList, result);

        verify(repositoryDepartment).findAll();
    }

    @Test //204
    void getAllDepartments_isEmpty(){
        when(repositoryDepartment.findAll()).thenReturn(new ArrayList<>());

        List<Department> result = departmentService.getAllDepartments();

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(departmentList, result);

        verify(repositoryDepartment).findAll();
    }

    @Test //200
    void findDepartmentsByFilters_success(){
        when(repositoryDepartment.findDepartmentsByFilters(anyString(), anyInt())).thenReturn(departmentList);

        List<Department> result = departmentService.findDepartmentsByFilters("gestion", 1);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(departmentList, result);

        verify(repositoryDepartment).findDepartmentsByFilters(anyString(), anyInt());
    }

    @Test //204
    void findDepartmentsByFilters_isEmpty(){
        when(repositoryDepartment.findDepartmentsByFilters(anyString(), anyInt())).thenReturn(new ArrayList<>());

        List<Department> result = departmentService.findDepartmentsByFilters("contaduria", 2);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(departmentList, result);

        verify(repositoryDepartment).findDepartmentsByFilters(anyString(), anyInt());
    }

}