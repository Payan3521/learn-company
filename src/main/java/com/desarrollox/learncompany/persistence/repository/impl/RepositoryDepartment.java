package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.persistence.mapper.DepartmentMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryDepartment;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryDepartment implements IRepositoryDepartment{

    private final JpaRepositoryDepartment jpaRepositoryDepartment;
    private final DepartmentMapper departmentMapper;
    
    @Override
    public Department save(Department department) {
        return departmentMapper.toDomain(jpaRepositoryDepartment.save(departmentMapper.toEntity(department)));
    }

    @Override
    public Optional<Department> update(Long id, Department department) {
        return jpaRepositoryDepartment.findById(id).map(departmentEntity -> {
            department.setId(id);
            return departmentMapper.toDomain(jpaRepositoryDepartment.save(departmentMapper.toEntity(department)));
        });
    }

    @Override
    public Optional<Department> findById(Long id) {
        return jpaRepositoryDepartment.findById(id).map(departmentMapper::toDomain);
    }

    @Override
    public Optional<Department> delete(Long id) {
        return jpaRepositoryDepartment.findById(id).map(departmentEntity -> {
            jpaRepositoryDepartment.delete(departmentEntity);
            return departmentMapper.toDomain(departmentEntity);
        });
    }

    @Override
    public List<Department> findAll() {
        return jpaRepositoryDepartment.findAll().stream().map(departmentMapper::toDomain).toList();
    }

    @Override
    public List<Department> findDepartmentsByFilters(String name, int hierarchy) {
        return jpaRepositoryDepartment.findByNameContainingAndHierarchy(name, hierarchy)
                .stream()
                .map(departmentMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositoryDepartment.existsById(id);
    }
    
}