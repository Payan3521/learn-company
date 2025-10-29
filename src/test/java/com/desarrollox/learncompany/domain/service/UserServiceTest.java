package com.desarrollox.learncompany.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.security.PasswordEncoderConfig;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.DepartmentNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.domain.service.impl.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private IRepositoryUser repositoryUser;

    @Mock
    private IRepositoryDepartment repositoryDepartment;

    @Mock
    private IRepositoryCourse repositoryCourse;

    @Mock
    private PasswordEncoderConfig passwordEncoderConfig;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private UserService userService;

    private Employee employee;
    private User employeeSaved;
    private Instructor instructor;
    private Instructor instructorSaved;

    private Department department;
    private List<User> userList;

    @BeforeEach
    void setUp(){
        department = new Department();
        department.setId(1L);
        department.setName("gestion");

        employee = new Employee();
        employee.setRole(Role.EMPLOYEE);
        employee.setEmail("email@gmail.com");
        employee.setDepartment(department);
        employee.setPassword("Password123");
        employee.setStatus(true);

        employeeSaved = new Employee();
        employeeSaved.setId(1L);
        employeeSaved.setRole(Role.EMPLOYEE);
        employeeSaved.setEmail("email@gmail.com");
        employeeSaved.setDepartment(department);
        employeeSaved.setPassword("hashedPassword");
        
        
        instructor = new Instructor();
        instructor.setId(2L);
        instructor.setRole(Role.INSTRUCTOR);
        instructor.setEmail("emailInstructor@gmail.com");
        instructor.setDepartment(department);
        instructor.setPassword("Password2233");
        instructor.setStatus(true);
        instructor.setBiography("Bio");
        instructor.setSpecialty("Java");

        instructorSaved = new Instructor();
        instructorSaved.setId(2L);
        instructorSaved.setRole(Role.INSTRUCTOR);
        instructorSaved.setEmail("emailInstructor@gmail.com");
        instructorSaved.setDepartment(department);
        instructorSaved.setPassword("hashedPassword");
        instructorSaved.setStatus(false);
        instructorSaved.setBiography("Nuevo Bio");
        instructorSaved.setSpecialty("Python");

        userList = new ArrayList<>();
        userList.add(employee);
    }

    @Test //201
    void createEmployee_success(){
        when(repositoryUser.findByEmailIncludingInactive(anyString())).thenReturn(Optional.empty());
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryDepartment.findById(anyLong())).thenReturn(Optional.of(department));
        when(passwordEncoderConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        when(repositoryUser.save(any(Employee.class))).thenReturn(employeeSaved);
       
        Employee result = userService.createEmployee(employee);

        assertNotNull(result);
        assertEquals(employeeSaved, result);
        assertEquals(employeeSaved.getPassword(), result.getPassword());
        assertNotEquals(employee.getPassword(), result.getPassword());

        verify(repositoryUser).findByEmailIncludingInactive(anyString());
        verify(repositoryDepartment).existsById(employeeSaved.getId());
        verify(repositoryDepartment).findById(employeeSaved.getId());
        verify(repositoryUser).save(any(Employee.class));
    }

    @Test //201
    void createEmployee_success_reactivando(){
        when(repositoryUser.findByEmailIncludingInactive(anyString())).thenReturn(Optional.of(employeeSaved));
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryDepartment.findById(anyLong())).thenReturn(Optional.of(department));
        when(passwordEncoderConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());

        when(repositoryUser.save(any(Employee.class))).thenReturn(employeeSaved);

        Employee result = userService.createEmployee(employee);

        assertNotNull(result);
        assertEquals(employeeSaved, result);
        assertEquals(employeeSaved.getPassword(), result.getPassword());
        assertNotEquals(employee.getPassword(), result.getPassword());

        verify(repositoryUser).findByEmailIncludingInactive(anyString());
        verify(repositoryDepartment).existsById(employeeSaved.getId());
        verify(repositoryDepartment).findById(employeeSaved.getId());
        verify(repositoryUser).save(any(Employee.class));
    }

    @Test //409
    void createEmployee_UserAlreadyRegistered(){
        when(repositoryUser.findByEmailIncludingInactive(anyString())).thenReturn(Optional.of(employee));

        UserAlreadyRegisteredException thrown = assertThrows(
            UserAlreadyRegisteredException.class, 
            () -> userService.createEmployee(employee)
        );

        assertNotNull(thrown);
        

        verify(repositoryUser).findByEmailIncludingInactive(anyString());
    }

    @Test //404
    void createEmployee_DepartmentNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(false);

        DepartmentNotFoundException thrown = assertThrows(
            DepartmentNotFoundException.class, 
            () -> userService.createEmployee(employee)
        );

        assertNotNull(thrown);
        verify(repositoryDepartment).existsById(anyLong());
    }

    @Test //201
    void createInstructor_success(){
        when(repositoryUser.findByEmailIncludingInactive(anyString())).thenReturn(Optional.empty());
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryDepartment.findById(anyLong())).thenReturn(Optional.of(department));
        when(passwordEncoderConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        when(repositoryUser.save(any(Instructor.class))).thenReturn(instructorSaved);
       
        Instructor result = userService.createInstructor(instructor);

        assertNotNull(result);
        assertEquals(instructorSaved, result);
        assertEquals(instructorSaved.getPassword(), result.getPassword());
        assertNotEquals(instructor.getPassword(), result.getPassword());

        verify(repositoryUser).findByEmailIncludingInactive(anyString());
        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryDepartment).findById(anyLong());
        verify(repositoryUser).save(any(Instructor.class));
    }

    @Test //201
    void createInstructor_success_reactivando(){
        when(repositoryUser.findByEmailIncludingInactive(anyString())).thenReturn(Optional.of(instructorSaved));
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryDepartment.findById(anyLong())).thenReturn(Optional.of(department));
        when(passwordEncoderConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());

        when(repositoryUser.save(any(Instructor.class))).thenReturn(instructorSaved);

        Instructor result = userService.createInstructor(instructor);

        assertNotNull(result);
        assertEquals(instructorSaved, result);
        assertEquals(instructorSaved.getPassword(), result.getPassword());
        assertNotEquals(instructor.getPassword(), result.getPassword());

        verify(repositoryUser).findByEmailIncludingInactive(anyString());
        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryDepartment).findById(anyLong());
        verify(repositoryUser).save(any(Instructor.class));
    }

    @Test //409
    void createInstructor_UserAlreadyRegistered(){
        when(repositoryUser.findByEmailIncludingInactive(anyString())).thenReturn(Optional.of(instructor));

        UserAlreadyRegisteredException thrown = assertThrows(
            UserAlreadyRegisteredException.class, 
            () -> userService.createInstructor(instructor)
            );

        assertNotNull(thrown);
        
        verify(repositoryUser).findByEmailIncludingInactive(anyString());
    }

    @Test //404
    void createInstructor_DepartmentNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(false);

        DepartmentNotFoundException thrown = assertThrows(
            DepartmentNotFoundException.class, 
            () -> userService.createInstructor(instructor)
        );
        
        assertNotNull(thrown);

        verify(repositoryUser).findByEmailIncludingInactive(anyString());

    }

    @Test //200
    void findById_success(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employeeSaved));

        Optional<User> result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(employeeSaved, result.get());
        assertEquals(employeeSaved.getId(), result.get().getId());

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
    }

    @Test //404
    void findById_UserNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class,
            () -> userService.findById(1L)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, never()).findById(anyLong());
    }

    @Test //200
    void findByEmail_success(){
        when(repositoryUser.existsByEmail(anyString())).thenReturn(true);
        when(repositoryUser.findByEmail(anyString())).thenReturn(Optional.of(employee));

        Optional<User> result = userService.findByEmail("ejemplo@gmail.com");

        assertNotNull(result);
        assertEquals(employee, result.get());
        assertEquals(employee.getEmail(), result.get().getEmail());

        verify(repositoryUser).existsByEmail(anyString());
        verify(repositoryUser).findByEmail(anyString());
    }

    @Test //404
    void findByEmail_UserNotFound(){
        when(repositoryUser.existsByEmail(anyString())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class,
            () -> userService.findByEmail("@ejemplo@gmail.com")
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsByEmail(anyString());
        verify(repositoryUser, never()).findByEmail(anyString());
    }

    @Test //200
    void findAll_success(){
        when(repositoryUser.findAll()).thenReturn(userList);

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertEquals(userList.size(), result.size());

        verify(repositoryUser).findAll();

    }

    @Test //204
    void findAll_isEmpty(){
        when(repositoryUser.findAll()).thenReturn(new ArrayList<>());

        List<User> result = userService.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repositoryUser).findAll();
    }

    @Test //200
    void updateUser_success(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(instructor));
        when(repositoryDepartment.findById(anyLong())).thenReturn(Optional.of(department));
        when(passwordEncoderConfig.passwordEncoder()).thenReturn(new BCryptPasswordEncoder());
        when(repositoryUser.update(anyLong(), any(User.class))).thenReturn(Optional.of(instructorSaved));

        Optional<User> result = userService.updateUser(instructor.getId(), instructor);

        assertTrue(result.isPresent());
        assertNotEquals(instructor.getBiography(), instructorSaved.getBiography());
        assertNotEquals(instructor.getSpecialty(), instructorSaved.getSpecialty());
        assertNotEquals(instructor.getPassword(), instructorSaved.getPassword());
    }

    @Test //404
    void updateUser_UserNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class, 
            () -> userService.updateUser(instructor.getId(), instructor)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, never()).update(instructor.getId(), instructor);
    }

    @Test //404
    void updateUser_DepartmentNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryDepartment.existsById(anyLong())).thenReturn(false);

        DepartmentNotFoundException thrown = assertThrows(
            DepartmentNotFoundException.class, 
            () -> userService.updateUser(instructor.getId(),instructor)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryUser, never()).update(instructor.getId(), instructor);
    }

    @Test //409
    void updateUser_UserAlreadyRegistered(){

        Instructor instructorLocal = new Instructor();
        instructorLocal.setId(2L);
        instructorLocal.setRole(Role.INSTRUCTOR);
        instructorLocal.setEmail("emailInstructorNuevo@gmail.com");
        instructorLocal.setDepartment(department);
        instructorLocal.setPassword("hashedPassword");
        instructorLocal.setStatus(true);
        instructorLocal.setBiography("Nuevo Bio");
        instructorLocal.setSpecialty("Python");

        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(instructorLocal));
        when(repositoryUser.existsByEmail(anyString())).thenReturn(true);
        
        UserAlreadyRegisteredException thrown = assertThrows(
            UserAlreadyRegisteredException.class, 
            () -> userService.updateUser(instructor.getId(), instructor)
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
        verify(repositoryUser).existsByEmail(anyString());
    }

    @Test //200
    void delete_success(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.delete(anyLong())).thenReturn(Optional.empty());

        Optional<User> result = userService.delete(instructor.getId());

        assertNotNull(result);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).delete(anyLong());
    }

    @Test //404
    void delete_UserNotFound(){
        when(repositoryUser.existsById(anyLong())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class, 
            () -> userService.delete(instructor.getId())  
        );

        assertNotNull(thrown);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, never()).delete(anyLong());
    }

    @Test //200
    void getRankingByDepartment_success(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);

        List<Employee> result = userService.getRankingByDepartment(department.getId());

        assertNotNull(result);

        verify(repositoryDepartment).existsById(anyLong());
    }

    @Test //404
    void getRankingByDepartment_DepartmentNotFound(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(false);

        DepartmentNotFoundException thrown = assertThrows(
            DepartmentNotFoundException.class, 
            () -> userService.getRankingByDepartment(department.getId())  
        );

        assertNotNull(thrown);

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryUser, never()).getRankingByDepartment(anyLong());
    }

    @Test //204
    void getRankingByDepartment_isEmpty(){
        when(repositoryDepartment.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.getRankingByDepartment(anyLong())).thenReturn(new ArrayList<>());

        List<Employee> result = userService.getRankingByDepartment(department.getId());

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repositoryDepartment).existsById(anyLong());
        verify(repositoryUser).getRankingByDepartment(anyLong());
    }

    @Test //200
    void findUsersByFilters_success(){
        when(repositoryUser.findUsersByFilters(department.getId(), instructor.getRole(), true)).thenReturn(userList);
        List<User> result = userService.findUsersByFilters(department.getId(), instructor.getRole(), true);

        assertNotNull(result);

        verify(repositoryUser).findUsersByFilters(department.getId(), instructor.getRole(), true);
    }

    @Test //204
    void findUsersByFilters_isEmpty(){
        when(repositoryUser.findUsersByFilters(department.getId(), instructor.getRole(), true)).
        thenReturn(new ArrayList<>());

        List<User> result = userService.findUsersByFilters(department.getId(), instructor.getRole(), true);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repositoryUser).findUsersByFilters(department.getId(), instructor.getRole(), true);
    }

    @Test //200
    void findEmployeesFinished_success(){
        when(repositoryUser.findEmployeesFinished()).thenReturn(List.of(employee));
        
        List<Employee> result = userService.findEmployeesFinished();

        assertNotNull(result);

        verify(repositoryUser).findEmployeesFinished();
    }

    @Test //204
    void findEmployeesFinished_isEmpty(){
        when(repositoryUser.findEmployeesFinished()).thenReturn(new ArrayList<>());

        List<Employee> result = userService.findEmployeesFinished();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repositoryUser).findEmployeesFinished();
    }

    @Test //200
    void findEmployeesFinishedByCourseId_success(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        
        List<Employee> result = userService.findEmployeesFinishedByCourseId(1L);

        assertNotNull(result);
        
        verify(repositoryCourse).existsById(anyLong());
    }

    @Test //404
    void findEmployeesFinishedByCourseId_CourseNotFound(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(false);

        CourseNotFoundException thrown = assertThrows(
            CourseNotFoundException.class, 
            () -> userService.findEmployeesFinishedByCourseId(1L)    
        );

        assertNotNull(thrown);

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryUser, never()).findEmployeesFinishedByCourseId(anyLong());
    }

    @Test //204
    void findEmployeesFinishedByCourseId_isEmpty(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findEmployeesFinishedByCourseId(anyLong())).thenReturn(List.of(employee));

        List<Employee> result = userService.findEmployeesFinishedByCourseId(1L);

        assertNotNull(result);

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryUser).findEmployeesFinishedByCourseId(anyLong());
    }

}