package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Department;

public interface IDepartmentService {
    Department createDepartment(Department department);
    Optional<Department> updateDepartment(Long id, Department department);
    Optional<Department> getDepartmentById(Long id);
    Optional<Department> deleteDepartment(Long id);
    List<Department> getAllDepartments();
    List<Department> findDepartmentsByFilters(String name, int hierarchy);
}