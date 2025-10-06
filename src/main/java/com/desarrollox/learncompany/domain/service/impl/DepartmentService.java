package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.exception.DepartmentAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.DepartmentNotFoundException;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.domain.service.IDepartmentService;
import lombok.RequiredArgsConstructor; 

@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService {

    private final IRepositoryDepartment repositoryDepartment;

    @Override
    public Department createDepartment(Department department) {
        if(repositoryDepartment.existsByName(department.getName())){
            throw new DepartmentAlreadyRegisteredException(department.getName());
        }
        return repositoryDepartment.save(department);
    }

    @Override
    public Optional<Department> updateDepartment(Long id, Department department) {
        if(!repositoryDepartment.existsById(id)){
            throw new DepartmentNotFoundException(id);
        }
        if(repositoryDepartment.existsByName(department.getName())){
            throw new DepartmentAlreadyRegisteredException(department.getName());
        }
        return repositoryDepartment.update(id, department);
    }

    @Override
    public Optional<Department> getDepartmentById(Long id) {
        if(repositoryDepartment.existsById(id)){
            return repositoryDepartment.findById(id);
        }
         throw new DepartmentNotFoundException(id);
    }

    @Override
    public Optional<Department> deleteDepartment(Long id) {
        if(!repositoryDepartment.existsById(id)){
            throw new DepartmentNotFoundException(id);
        }
        return repositoryDepartment.delete(id);
    }

    @Override
    public List<Department> getAllDepartments() {
        return repositoryDepartment.findAll();
    }

    @Override
    public List<Department> findDepartmentsByFilters(String name, int hierarchy) {
        return repositoryDepartment.findDepartmentsByFilters(name, hierarchy);
    }
    
}