package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryEmployeeBadge;
import com.desarrollox.learncompany.domain.model.EmployeeBadge;
import com.desarrollox.learncompany.persistence.mapper.EmployeeBadgeMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryEmployeeBadge;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryEmployeeBadge implements IRepositoryEmployeeBadge {

    private final JpaRepositoryEmployeeBadge jpaRepositoryEmployeeBadge;
    private final EmployeeBadgeMapper employeeBadgeMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public EmployeeBadge save(EmployeeBadge employeeBadge) {
        loggingService.logInfo("Iniciando guardado de EmployeeBadge para employeeId: {}, badgeId: {}", 
                employeeBadge.getEmployee().getId() != null ? employeeBadge.getEmployee().getId() : "null",
                employeeBadge.getBadge().getId() != null ? employeeBadge.getBadge().getId() : "null");
        try {
            EmployeeBadge savedEmployeeBadge = employeeBadgeMapper.toDomain(
                    jpaRepositoryEmployeeBadge.save(employeeBadgeMapper.toEntity(employeeBadge))
            );
            loggingService.logInfo("EmployeeBadge guardado exitosamente con ID: {}, employeeId: {}, badgeId: {}", 
                    savedEmployeeBadge.getId(), 
                    savedEmployeeBadge.getEmployee().getId(), 
                    savedEmployeeBadge.getBadge().getId());
            return savedEmployeeBadge;
        } catch (Exception e) {
            loggingService.logError("Error al guardar EmployeeBadge para employeeId: {}, badgeId: {}: {}", 
                    employeeBadge.getEmployee().getId() != null ? employeeBadge.getEmployee().getId() : "null",
                    employeeBadge.getBadge().getId() != null ? employeeBadge.getBadge().getId() : "null",
                    e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<EmployeeBadge> findById(Long id) {
        loggingService.logInfo("Obteniendo EmployeeBadge con ID: {}", id);
        try {
            Optional<EmployeeBadge> employeeBadge = jpaRepositoryEmployeeBadge.findById(id)
                    .map(employeeBadgeMapper::toDomain);
            if (employeeBadge.isPresent()) {
                loggingService.logInfo("EmployeeBadge ID {} obtenido exitosamente", id);
            } else {
                loggingService.logWarning("EmployeeBadge con ID {} no encontrado", id);
            }
            return employeeBadge;
        } catch (Exception e) {
            loggingService.logError("Error al obtener EmployeeBadge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<EmployeeBadge> findAll() {
        loggingService.logInfo("Obteniendo todos los EmployeeBadges");
        try {
            List<EmployeeBadge> employeeBadges = jpaRepositoryEmployeeBadge.findAll()
                    .stream()
                    .map(employeeBadgeMapper::toDomain)
                    .collect(Collectors.toList());
            if (employeeBadges.isEmpty()) {
                loggingService.logWarning("No se encontraron EmployeeBadges");
            } else {
                loggingService.logInfo("Se encontraron {} EmployeeBadges", employeeBadges.size());
            }
            return employeeBadges;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los EmployeeBadges: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<EmployeeBadge> delete(Long id) {
        loggingService.logInfo("Iniciando eliminación de EmployeeBadge con ID: {}", id);
        try {
            Optional<EmployeeBadge> employeeBadge = jpaRepositoryEmployeeBadge.findById(id)
                    .map(entity -> {
                        jpaRepositoryEmployeeBadge.delete(entity);
                        EmployeeBadge deletedEmployeeBadge = employeeBadgeMapper.toDomain(entity);
                        loggingService.logInfo("EmployeeBadge ID {} eliminado exitosamente", id);
                        return deletedEmployeeBadge;
                    });
            if (employeeBadge.isEmpty()) {
                loggingService.logWarning("EmployeeBadge con ID {} no encontrado para eliminación", id);
            }
            return employeeBadge;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar EmployeeBadge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de EmployeeBadge con ID: {}", id);
        try {
            boolean exists = jpaRepositoryEmployeeBadge.existsById(id);
            loggingService.logDebug("EmployeeBadge con ID {} existe: {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de EmployeeBadge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}