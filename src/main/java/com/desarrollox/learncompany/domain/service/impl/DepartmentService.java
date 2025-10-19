package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
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
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = false)
    @Override
    public Department createDepartment(Department department) {
        loggingService.logInfo("Iniciando creación de Department con nombre: {}", department.getName());
        try {
            if (repositoryDepartment.existsByName(department.getName())) {
                loggingService.logError("Department con nombre {} ya está registrado", department.getName());
                throw new DepartmentAlreadyRegisteredException(department.getName());
            }
            Department savedDepartment = repositoryDepartment.save(department);
            loggingService.logInfo("Department creado exitosamente con ID: {} y nombre: {}", 
                    savedDepartment.getId(), savedDepartment.getName());
            return savedDepartment;
        } catch (Exception e) {
            loggingService.logError("Error al crear Department con nombre {}: {}", department.getName(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public Optional<Department> updateDepartment(Long id, Department department) {
        loggingService.logInfo("Iniciando actualización de Department con ID: {} y nuevo nombre: {}", id, department.getName());
        try {
            if (!repositoryDepartment.existsById(id)) {
                loggingService.logError("Department con ID {} no encontrado", id);
                throw new DepartmentNotFoundException(id);
            }
            if (repositoryDepartment.existsByName(department.getName())) {
                loggingService.logError("Department con nombre {} ya está registrado", department.getName());
                throw new DepartmentAlreadyRegisteredException(department.getName());
            }
            Optional<Department> updatedDepartment = repositoryDepartment.update(id, department);
            loggingService.logInfo("Department ID {} actualizado exitosamente", id);
            return updatedDepartment;
        } catch (Exception e) {
            loggingService.logError("Error al actualizar Department ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Department> getDepartmentById(Long id) {
        loggingService.logInfo("Obteniendo Department con ID: {}", id);
        try {
            if (!repositoryDepartment.existsById(id)) {
                loggingService.logError("Department con ID {} no encontrado", id);
                throw new DepartmentNotFoundException(id);
            }
            Optional<Department> department = repositoryDepartment.findById(id);
            loggingService.logInfo("Department ID {} obtenido exitosamente", id);
            return department;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Department ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public Optional<Department> deleteDepartment(Long id) {
        loggingService.logInfo("Iniciando eliminación de Department con ID: {}", id);
        try {
            if (!repositoryDepartment.existsById(id)) {
                loggingService.logError("Department con ID {} no encontrado", id);
                throw new DepartmentNotFoundException(id);
            }
            Optional<Department> deletedDepartment = repositoryDepartment.delete(id);
            loggingService.logInfo("Department ID {} eliminado exitosamente", id);
            return deletedDepartment;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Department ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Department> getAllDepartments() {
        loggingService.logInfo("Obteniendo todos los Departments");
        try {
            List<Department> departments = repositoryDepartment.findAll();
            if (departments.isEmpty()) {
                loggingService.logWarning("No se encontraron Departments");
            } else {
                loggingService.logInfo("Se encontraron {} Departments", departments.size());
            }
            return departments;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Departments: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Department> findDepartmentsByFilters(String name, int hierarchy) {
        loggingService.logInfo("Obteniendo Departments con filtros: name={}, hierarchy={}", name, hierarchy);
        try {
            List<Department> departments = repositoryDepartment.findDepartmentsByFilters(name, hierarchy);
            if (departments.isEmpty()) {
                loggingService.logWarning("No se encontraron Departments con filtros: name={}, hierarchy={}", name, hierarchy);
            } else {
                loggingService.logInfo("Se encontraron {} Departments con filtros: name={}, hierarchy={}", 
                        departments.size(), name, hierarchy);
            }
            return departments;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Departments con filtros: name={}, hierarchy={}: {}", 
                    name, hierarchy, e.getMessage(), e);
            throw e;
        }
    }
}