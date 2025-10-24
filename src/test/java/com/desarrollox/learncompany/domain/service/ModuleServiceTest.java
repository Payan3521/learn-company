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
import com.desarrollox.learncompany.domain.accessDb.IRepositoryModule;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.ModuleNotFoundException;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.domain.service.impl.ModuleService;

@ExtendWith(MockitoExtension.class)
public class ModuleServiceTest {
    
    @Mock
    private IRepositoryModule repositoryModule;

    @Mock
    private IRepositoryCourse repositoryCourse;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private ModuleService moduleService;

    private Module module;
    private Course course;
    private List<Module> moduleList;

    @BeforeEach
    void setUp(){
        course = new Course();
        course.setId(1L);
        course.setDuration(500);
        
        module = new Module();
        module.setId(1L);
        module.setCourse(course);
        
        moduleList = new ArrayList<>();
        moduleList.add(module);
    }

    @Test //201
    void createModule_success(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryCourse.findById(anyLong())).thenReturn(Optional.of(course));
        when(repositoryModule.save(any(Module.class))).thenReturn(module);

        Module result = moduleService.createModule(module);

        assertNotNull(result);
        assertEquals(module, result);
        assertEquals(module.getId(), result.getId());

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryCourse).findById(anyLong());
        verify(repositoryModule).save(any(Module.class));
    }

    @Test //404
    void createModule_CourseNotFound(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(false);

        CourseNotFoundException thrown = assertThrows(
            CourseNotFoundException.class, 
            () -> moduleService.createModule(module)
        );

        assertNotNull(thrown);

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryCourse, never()).findById(anyLong());
        verify(repositoryModule, never()).save(any(Module.class));
    }

    @Test //200
    void getModuleById_success(){
        when(repositoryModule.existsById(anyLong())).thenReturn(true);
        when(repositoryModule.findById(anyLong())).thenReturn(Optional.of(module));

        Optional<Module> result = moduleService.getModuleById(1L);

        assertTrue(result.isPresent());
        assertEquals(module, result.get());
        assertEquals(1L, result.get().getId());

        verify(repositoryModule).existsById(anyLong());
        verify(repositoryModule).findById(anyLong());
    }

    @Test //404
    void getModuleById_ModuleNotFound(){
        when(repositoryModule.existsById(anyLong())).thenReturn(false);
        
        ModuleNotFoundException thrown = assertThrows(
            ModuleNotFoundException.class, 
            () -> moduleService.getModuleById(1L)
        );

        assertNotNull(thrown);

        verify(repositoryModule).existsById(anyLong());
        verify(repositoryModule, never()).findById(anyLong());
    }

    @Test //200
    void getAllModules_success(){
        when(repositoryModule.findAll()).thenReturn(moduleList);

        List<Module> result = moduleService.getAllModules();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(moduleList, result);

        verify(repositoryModule).findAll();
    }

    @Test //204
    void getAllModules_isEmpty(){
        when(repositoryModule.findAll()).thenReturn(new ArrayList<>());

        List<Module> result = moduleService.getAllModules();

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(moduleList, result);

        verify(repositoryModule).findAll();
    }

    @Test //200
    void deleteModule_success(){
        when(repositoryModule.existsById(anyLong())).thenReturn(true);
        when(repositoryModule.delete(anyLong())).thenReturn(Optional.of(module));

        Optional<Module> result = moduleService.deleteModule(1L);

        assertNotNull(result);
        assertEquals(1L, result.get().getId());

        verify(repositoryModule).existsById(anyLong());
        verify(repositoryModule).delete(anyLong());
    }

    @Test //404
    void deleteModule_ModuleNotFound(){
        when(repositoryModule.existsById(anyLong())).thenReturn(false);

        ModuleNotFoundException thrown = assertThrows(
            ModuleNotFoundException.class, 
            () -> moduleService.deleteModule(1L)
        );

        assertNotNull(thrown);

        verify(repositoryModule).existsById(anyLong());
        verify(repositoryModule, never()).delete(anyLong());
    }

    @Test //200
    void getModulesByCourseId_success(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryModule.findModulesByCourseId(anyLong())).thenReturn(moduleList);

        List<Module> result = moduleService.getModulesByCourseId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(moduleList, result);

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryModule).findModulesByCourseId(anyLong());
    }

    @Test //404
    void getModulesByCourseId_CourseNotFound(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(false);

        CourseNotFoundException thrown = assertThrows(
            CourseNotFoundException.class, 
            () -> moduleService.getModulesByCourseId(1L)
        );

        assertNotNull(thrown);

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryModule, never()).findModulesByCourseId(anyLong());
    }

    @Test //204
    void getModulesByCourseId_isEmpty(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryModule.findModulesByCourseId(anyLong())).thenReturn(new ArrayList<>());

        List<Module> result = moduleService.getModulesByCourseId(1L);

        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
        assertNotEquals(moduleList, result);

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryModule).findModulesByCourseId(anyLong());
    }

}