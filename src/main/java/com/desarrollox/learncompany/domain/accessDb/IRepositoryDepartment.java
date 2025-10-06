package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Department;

public interface IRepositoryDepartment { 
    Department save(Department department);
    Optional<Department> update(Long id, Department department);
    Optional<Department> findById(Long id);
    Optional<Department> delete(Long id);
    List<Department> findAll();
    boolean existsById(Long id);
    List<Department> findDepartmentsByFilters(String name, int hierarchy);
    boolean existsByName(String name);
}