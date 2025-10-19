package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.persistence.entity.UserEntity;
import com.desarrollox.learncompany.persistence.mapper.EmployeeMapper;
import com.desarrollox.learncompany.persistence.mapper.PolymorphicUserMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryUser;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryUser implements IRepositoryUser {

    private final JpaRepositoryUser jpaRepositoryUser;
    private final PolymorphicUserMapper polymorphicUserMapper;
    private final EmployeeMapper employeeMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public User save(User user) {
        loggingService.logInfo("Iniciando guardado de User con email: {}", 
                truncateEmail(user != null && user.getEmail() != null ? user.getEmail() : "null"));
        try {
            User savedUser = polymorphicUserMapper.toDomain(
                    jpaRepositoryUser.save(polymorphicUserMapper.toEntity(user))
            );
            loggingService.logInfo("User guardado exitosamente con ID: {} y email: {}", 
                    savedUser.getId(), truncateEmail(savedUser.getEmail()));
            return savedUser;
        } catch (Exception e) {
            loggingService.logError("Error al guardar User con email {}: {}", 
                    truncateEmail(user != null && user.getEmail() != null ? user.getEmail() : "null"), 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        loggingService.logInfo("Obteniendo User con ID: {}", id);
        try {
            Optional<User> user = jpaRepositoryUser.findByIdAndStatusTrue(id)
                    .map(polymorphicUserMapper::toDomain);
            if (user.isPresent()) {
                loggingService.logInfo("User ID {} obtenido exitosamente", id);
            } else {
                loggingService.logWarning("User con ID {} no encontrado o inactivo", id);
            }
            return user;
        } catch (Exception e) {
            loggingService.logError("Error al obtener User ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        loggingService.logInfo("Obteniendo User con email: {}", truncateEmail(email));
        try {
            Optional<User> user = jpaRepositoryUser.findByEmailAndStatusTrue(email)
                    .map(polymorphicUserMapper::toDomain);
            if (user.isPresent()) {
                loggingService.logInfo("User con email {} obtenido exitosamente", truncateEmail(email));
            } else {
                loggingService.logWarning("User con email {} no encontrado o inactivo", truncateEmail(email));
            }
            return user;
        } catch (Exception e) {
            loggingService.logError("Error al obtener User con email {}: {}", truncateEmail(email), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<User> findAll() {
        loggingService.logInfo("Obteniendo todos los Users activos");
        try {
            List<User> users = jpaRepositoryUser.findAllByStatusTrue()
                    .stream()
                    .map(polymorphicUserMapper::toDomain)
                    .collect(Collectors.toList());
            if (users.isEmpty()) {
                loggingService.logWarning("No se encontraron Users activos");
            } else {
                loggingService.logInfo("Se encontraron {} Users activos", users.size());
            }
            return users;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Users activos: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<User> update(Long id, User user) {
        loggingService.logInfo("Iniciando actualización de User con ID: {}", id);
        try {
            Optional<User> updatedUser = jpaRepositoryUser.findByIdAndStatusTrue(id)
                    .map(userEntity -> {
                        user.setId(id);
                        UserEntity updatedUserEntity = jpaRepositoryUser.save(polymorphicUserMapper.toEntity(user));
                        User result = polymorphicUserMapper.toDomain(updatedUserEntity);
                        loggingService.logInfo("User ID {} actualizado exitosamente", id);
                        return result;
                    });
            if (updatedUser.isEmpty()) {
                loggingService.logWarning("User con ID {} no encontrado o inactivo para actualización", id);
            }
            return updatedUser;
        } catch (Exception e) {
            loggingService.logError("Error al actualizar User ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<User> delete(Long id) {
        loggingService.logInfo("Iniciando eliminación lógica de User con ID: {}", id);
        try {
            Optional<User> deletedUser = jpaRepositoryUser.findByIdAndStatusTrue(id)
                    .map(userEntity -> {
                        userEntity.setStatus(false);
                        UserEntity deletedUserEntity = jpaRepositoryUser.save(userEntity);
                        User result = polymorphicUserMapper.toDomain(deletedUserEntity);
                        loggingService.logInfo("User ID {} eliminado lógicamente exitosamente", id);
                        return result;
                    });
            if (deletedUser.isEmpty()) {
                loggingService.logWarning("User con ID {} no encontrado o ya inactivo para eliminación", id);
            }
            return deletedUser;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar lógicamente User ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Employee> getRankingByDepartment(Long departmentId) {
        loggingService.logInfo("Obteniendo ranking de Employees para departmentId: {}", departmentId);
        try {
            List<Employee> employees = jpaRepositoryUser.findByDepartmentOrderByPuntosDesc(departmentId)
                    .stream()
                    .map(employeeMapper::toDomain)
                    .collect(Collectors.toList());
            if (employees.isEmpty()) {
                loggingService.logWarning("No se encontraron Employees para departmentId: {}", departmentId);
            } else {
                loggingService.logInfo("Se encontraron {} Employees para departmentId: {}", employees.size(), departmentId);
            }
            return employees;
        } catch (Exception e) {
            loggingService.logError("Error al obtener ranking de Employees para departmentId {}: {}", departmentId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<User> findUsersByFilters(Long departmentId, Role role, boolean status) {
        loggingService.logInfo("Obteniendo Users con filtros: departmentId={}, role={}, status={}", 
                departmentId != null ? departmentId : "null", role, status);
        try {
            List<User> users = jpaRepositoryUser.findByFilters(departmentId, role, status)
                    .stream()
                    .map(polymorphicUserMapper::toDomain)
                    .collect(Collectors.toList());
            if (users.isEmpty()) {
                loggingService.logWarning("No se encontraron Users con filtros: departmentId={}, role={}, status={}", 
                        departmentId != null ? departmentId : "null", role, status);
            } else {
                loggingService.logInfo("Se encontraron {} Users con filtros: departmentId={}, role={}, status={}", 
                        users.size(), departmentId != null ? departmentId : "null", role, status);
            }
            return users;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Users con filtros: departmentId={}, role={}, status={}: {}", 
                    departmentId != null ? departmentId : "null", role, status, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de User con ID: {}", id);
        try {
            boolean exists = jpaRepositoryUser.existsByIdAndStatusTrue(id);
            loggingService.logDebug("User con ID {} existe (activo): {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de User ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        loggingService.logInfo("Verificando existencia de User con email: {}", truncateEmail(email));
        try {
            boolean exists = jpaRepositoryUser.existsByEmailAndStatusTrue(email);
            loggingService.logDebug("User con email {} existe (activo): {}", truncateEmail(email), exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de User con email {}: {}", truncateEmail(email), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<User> findByEmailIncludingInactive(String email) {
        loggingService.logInfo("Obteniendo User con email (incluyendo inactivos): {}", truncateEmail(email));
        try {
            Optional<User> user = jpaRepositoryUser.findByEmail(email)
                    .map(polymorphicUserMapper::toDomain);
            if (user.isPresent()) {
                loggingService.logInfo("User con email {} obtenido exitosamente (incluyendo inactivos)", truncateEmail(email));
            } else {
                loggingService.logWarning("User con email {} no encontrado (incluyendo inactivos)", truncateEmail(email));
            }
            return user;
        } catch (Exception e) {
            loggingService.logError("Error al obtener User con email {} (incluyendo inactivos): {}", 
                    truncateEmail(email), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Employee> findEmployeesFinished() {
        loggingService.logInfo("Obteniendo Employees que han finalizado");
        try {
            List<Employee> employees = jpaRepositoryUser.findEmployeesFinished()
                    .stream()
                    .map(employeeMapper::toDomain)
                    .collect(Collectors.toList());
            if (employees.isEmpty()) {
                loggingService.logWarning("No se encontraron Employees que han finalizado");
            } else {
                loggingService.logInfo("Se encontraron {} Employees que han finalizado", employees.size());
            }
            return employees;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Employees que han finalizado: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Employee> findEmployeesFinishedByCourseId(Long id) {
        loggingService.logInfo("Obteniendo Employees que han finalizado para courseId: {}", id);
        try {
            List<Employee> employees = jpaRepositoryUser.findEmployeesFinishedByCourseId(id)
                    .stream()
                    .map(employeeMapper::toDomain)
                    .collect(Collectors.toList());
            if (employees.isEmpty()) {
                loggingService.logWarning("No se encontraron Employees que han finalizado para courseId: {}", id);
            } else {
                loggingService.logInfo("Se encontraron {} Employees que han finalizado para courseId: {}", employees.size(), id);
            }
            return employees;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Employees que han finalizado para courseId {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // Método auxiliar para truncar correos electrónicos en los logs
    private String truncateEmail(String email) {
        if (email == null) {
            return "null";
        }
        int atIndex = email.indexOf('@');
        if (atIndex > 0 && email.length() > 10) {
            return email.substring(0, Math.min(4, atIndex)) + "****" + email.substring(atIndex);
        }
        return email.length() > 10 ? email.substring(0, 10) + "..." : email;
    }
}