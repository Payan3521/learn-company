package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryDepartment;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.persistence.mapper.DepartmentMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryDepartment;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryDepartment implements IRepositoryDepartment {

    private final JpaRepositoryDepartment jpaRepositoryDepartment;
    private final DepartmentMapper departmentMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public Department save(Department department) {
        loggingService.logInfo("Iniciando guardado de Department con nombre: {}", truncateName(department.getName()));
        try {
            Department savedDepartment = departmentMapper.toDomain(
                    jpaRepositoryDepartment.save(departmentMapper.toEntity(department))
            );
            loggingService.logInfo("Department guardado exitosamente con ID: {} y nombre: {}", 
                    savedDepartment.getId(), truncateName(savedDepartment.getName()));
            return savedDepartment;
        } catch (Exception e) {
            loggingService.logError("Error al guardar Department con nombre {}: {}", 
                    truncateName(department.getName()), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Department> update(Long id, Department department) {
        loggingService.logInfo("Iniciando actualización de Department con ID: {}", id);
        try {
            Optional<Department> updatedDepartment = jpaRepositoryDepartment.findById(id).map(departmentEntity -> {
                department.setId(id);
                Department savedDepartment = departmentMapper.toDomain(
                        jpaRepositoryDepartment.save(departmentMapper.toEntity(department))
                );
                loggingService.logInfo("Department ID {} actualizado exitosamente con nombre: {}", 
                        id, truncateName(savedDepartment.getName()));
                return savedDepartment;
            });
            if (updatedDepartment.isEmpty()) {
                loggingService.logWarning("Department con ID {} no encontrado para actualización", id);
            }
            return updatedDepartment;
        } catch (Exception e) {
            loggingService.logError("Error al actualizar Department ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Department> findById(Long id) {
        loggingService.logInfo("Obteniendo Department con ID: {}", id);
        try {
            Optional<Department> department = jpaRepositoryDepartment.findById(id)
                    .map(departmentMapper::toDomain);
            if (department.isPresent()) {
                loggingService.logInfo("Department ID {} obtenido exitosamente", id);
            } else {
                loggingService.logWarning("Department con ID {} no encontrado", id);
            }
            return department;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Department ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Department> delete(Long id) {
        loggingService.logInfo("Iniciando eliminación de Department con ID: {}", id);
        try {
            Optional<Department> department = jpaRepositoryDepartment.findById(id).map(departmentEntity -> {
                jpaRepositoryDepartment.delete(departmentEntity);
                Department deletedDepartment = departmentMapper.toDomain(departmentEntity);
                loggingService.logInfo("Department ID {} eliminado exitosamente", id);
                return deletedDepartment;
            });
            if (department.isEmpty()) {
                loggingService.logWarning("Department con ID {} no encontrado para eliminación", id);
            }
            return department;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Department ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Department> findAll() {
        loggingService.logInfo("Obteniendo todos los Departments");
        try {
            List<Department> departments = jpaRepositoryDepartment.findAll()
                    .stream()
                    .map(departmentMapper::toDomain)
                    .toList();
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

    @Override
    public List<Department> findDepartmentsByFilters(String name, int hierarchy) {
        loggingService.logInfo("Obteniendo Departments con filtros: name={}, hierarchy={}", truncateName(name), hierarchy);
        try {
            List<Department> departments = jpaRepositoryDepartment.findByNameContainingAndHierarchy(name, hierarchy)
                    .stream()
                    .map(departmentMapper::toDomain)
                    .collect(Collectors.toList());
            if (departments.isEmpty()) {
                loggingService.logWarning("No se encontraron Departments con filtros: name={}, hierarchy={}", 
                        truncateName(name), hierarchy);
            } else {
                loggingService.logInfo("Se encontraron {} Departments con filtros: name={}, hierarchy={}", 
                        departments.size(), truncateName(name), hierarchy);
            }
            return departments;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Departments con filtros: name={}, hierarchy={}: {}", 
                    truncateName(name), hierarchy, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de Department con ID: {}", id);
        try {
            boolean exists = jpaRepositoryDepartment.existsById(id);
            loggingService.logDebug("Department con ID {} existe: {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de Department ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsByName(String name) {
        loggingService.logInfo("Verificando existencia de Department con nombre: {}", truncateName(name));
        try {
            boolean exists = jpaRepositoryDepartment.existsByName(name);
            loggingService.logDebug("Department con nombre {} existe: {}", truncateName(name), exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de Department con nombre {}: {}", 
                    truncateName(name), e.getMessage(), e);
            throw e;
        }
    }

    // Método auxiliar para truncar nombres largos en los logs
    private String truncateName(String name) {
        if (name == null) {
            return "null";
        }
        return name.length() > 30 ? name.substring(0, 30) + "..." : name;
    }
}