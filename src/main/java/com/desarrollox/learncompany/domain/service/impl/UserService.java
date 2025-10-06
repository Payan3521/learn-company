package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.DepartmentNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.service.IUserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final IRepositoryUser repositoryUser;
    private final IRepositoryDepartment repositoryDepartment;

    @Override
    public Employee createEmployee(Employee employee) {

        employee.setDepartment(repositoryDepartment.findById(employee.getDepartment().getId()).get());

        if(repositoryUser.existsByEmail(employee.getEmail())){
            throw new UserAlreadyRegisteredException(employee.getEmail());
        } 

        if(!repositoryDepartment.existsById(employee.getDepartment().getId())){
            throw new DepartmentNotFoundException(employee.getDepartment().getId());
        }

        return (Employee) repositoryUser.save(employee);
    }

    @Override
    public Instructor createInstructor(Instructor instructor) {
        
        if(repositoryUser.existsByEmail(instructor.getEmail())){
            throw new UserAlreadyRegisteredException(instructor.getEmail());
        } 

        return (Instructor) repositoryUser.save(instructor);
    }

    @Override
    public Optional<User> findById(Long id) {

        if(repositoryUser.existsById(id)){
            return repositoryUser.findById(id);
        } 

        throw new UserNotFoundException(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        if(repositoryUser.existsByEmail(email)){
            return repositoryUser.findByEmail(email);
        } 

        throw new UserNotFoundException(email);
    }

    @Override
    public List<User> findAll() {
        return repositoryUser.findAll();
    }

    @Override
    public Optional<User> updateUser(Long id, User user) {
        if(repositoryUser.existsById(id)){
            return repositoryUser.update(id, user);
        }
        throw new UserNotFoundException(id);
    }


    @Override
    public Optional<User> delete(Long id) {
        if (repositoryUser.existsById(id)) {
            return repositoryUser.delete(id);
        } 
        throw new UserNotFoundException(id);
    }

    @Override
    public List<Employee> getRankingByDepartment(Long departmentId) {
        return repositoryUser.getRankingByDepartment(departmentId);
    }

    @Override
    public List<User> findUsersByFilters(Long departmentId, String role, boolean status) {
        return repositoryUser.findUsersByFilters(departmentId, role, status);
    }

}