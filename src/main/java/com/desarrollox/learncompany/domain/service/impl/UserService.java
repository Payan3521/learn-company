package com.desarrollox.learncompany.domain.service.impl;

import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.model.Employee;
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
    
}