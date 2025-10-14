package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(readOnly = false)
    @Override
    public Department createDepartment(Department department) {
        if(repositoryDepartment.existsByName(department.getName())){
            throw new DepartmentAlreadyRegisteredException(department.getName());
        }
        return repositoryDepartment.save(department);
    }

    @Transactional(readOnly = false)
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

    @Transactional(readOnly = true)
    @Override
    public Optional<Department> getDepartmentById(Long id) {
        if(repositoryDepartment.existsById(id)){
            return repositoryDepartment.findById(id);
        }
         throw new DepartmentNotFoundException(id);
    }

    @Transactional(readOnly = false)
    @Override
    public Optional<Department> deleteDepartment(Long id) {
        if(!repositoryDepartment.existsById(id)){
            throw new DepartmentNotFoundException(id);
        }
        return repositoryDepartment.delete(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Department> getAllDepartments() {
        return repositoryDepartment.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Department> findDepartmentsByFilters(String name, int hierarchy) {
        return repositoryDepartment.findDepartmentsByFilters(name, hierarchy);
    }
    
}