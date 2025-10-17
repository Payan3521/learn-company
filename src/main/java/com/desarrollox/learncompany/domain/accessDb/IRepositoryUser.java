package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.model.User.Role;

public interface IRepositoryUser {
    User save (User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailIncludingInactive(String email);
    List<User> findAll();
    Optional<User> update(Long id, User user);
    Optional<User> delete(Long id);
    List<Employee> getRankingByDepartment(Long departmentId);
    List<User> findUsersByFilters(Long departmentId, Role role, boolean status);
    boolean existsById(Long id);
    boolean existsByEmail(String email);
    List<Employee> findEmployeesFinished();
    List<Employee> findEmployeesFinishedByCourseId(Long id);
}