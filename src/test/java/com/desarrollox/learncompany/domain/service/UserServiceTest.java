package com.desarrollox.learncompany.domain.service;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.core.security.PasswordEncoderConfig;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.domain.service.impl.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
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

    private User employee;
    private User instructor;
    private Department department;
    private List<User> userList;

    @BeforeEach
    void setUp(){
        department = new Department();
        department.setId(1L);
        department.setName("gestion");

        employee = new Employee();
        employee.setId(1L);
        employee.setRole(Role.EMPLOYEE);
        employee.setDepartment(department);
        
        instructor = new Instructor();
        instructor.setId(2L);
        instructor.setDepartment(department);
        instructor.setRole(Role.INSTRUCTOR);
    }

    @Test //201
    void createEmployee_success(){

    }

    @Test //201
    void createEmployee_success_reactivando(){

    }

    @Test //409
    void createEmployee_UserAlreadyRegistered(){

    }

    @Test //404
    void createEmployee_DepartmentNotFound(){

    }

    @Test //201
    void createInstructor_success(){

    }

    @Test //201
    void createInstructor_success_reactivando(){

    }

    @Test //409
    void createInstructor_UserAlreadyRegistered(){

    }

    @Test //404
    void createInstructor_DepartmentNotFound(){

    }

    @Test //200
    void findById_success(){

    }

    @Test //404
    void findById_UserNotFound(){

    }

    @Test //200
    void findByEmail_success(){

    }

    @Test //404
    void findByEmail_UserNotFound(){

    }

    @Test //200
    void findAll_success(){

    }

    @Test //204
    void findAll_isEmpty(){

    }

    @Test //200
    void updateUser_success(){

    }

    @Test //404
    void updateUser_UserNotFound(){

    }

    @Test //404
    void updateUser_DepartmentNotFound(){

    }

    @Test //409
    void updateUser_UserAlreadyRegistered(){

    }

    @Test //200
    void delete_success(){

    }

    @Test //404
    void delete_UserNotFound(){

    }

    @Test //200
    void getRankingByDepartment_success(){

    }

    @Test //404
    void getRankingByDepartment_DepartmentNotFound(){

    }

    @Test //204
    void getRankingByDepartment_isEmpty(){

    }

    @Test //200
    void findUsersByFilters_success(){

    }

    @Test //204
    void findUsersByFilters_isEmpty(){

    }

    @Test //200
    void findEmployeesFinished_success(){

    }

    @Test //204
    void findEmployeesFinished_isEmpty(){

    }

    @Test //200
    void findEmployeesFinishedByCourseId_success(){

    }

    @Test //404
    void findEmployeesFinishedByCourseId_CourseNotFound(){

    }

    @Test //204
    void findEmployeesFinishedByCourseId_isEmpty(){

    }

}