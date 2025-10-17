package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.model.User.Role;

public interface IUserService {
    Employee createEmployee(Employee employee);
    Instructor createInstructor(Instructor instructor);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    Optional<User> updateUser(Long id, User user);
    Optional<User> delete(Long id);
    List<Employee> getRankingByDepartment(Long departmentId);
    List<User> findUsersByFilters(Long departmentId, Role role, boolean status);
    List<Employee> findEmployeesFinished();
    List<Employee> findEmployeesFinishedByCourseId(Long id);
}