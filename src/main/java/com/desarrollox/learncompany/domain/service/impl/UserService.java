package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.service.IUserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final IRepositoryUser repositoryUser;

    @Override
    public Employee createEmployee(Employee employee) {
        return (Employee) repositoryUser.save(employee);
    }

    @Override
    public Instructor createInstructor(Instructor instructor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createInstructor'");
    }

    @Override
    public Optional<User> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Optional<User> findByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }

    @Override
    public List<User> findByDepartmentId(Long departmentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByDepartmentId'");
    }

    @Override
    public Optional<User> updateUser(Long id, User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public Optional<User> delete(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<Employee> getRankingByDepartment(Long departmentId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRankingByDepartment'");
    }

    @Override
    public List<User> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}