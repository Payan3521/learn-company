package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryBadge;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryEmployeeBadge;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.BadgeNotFoundException;
import com.desarrollox.learncompany.domain.exception.EmployeeBadgeAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.EmployeeBadgeNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.EmployeeBadge;
import com.desarrollox.learncompany.domain.service.IEmployeeBadgeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeBadgeService implements IEmployeeBadgeService {

    private final IRepositoryBadge repositoryBadge;
    private final IRepositoryUser repositoryUser;
    private final IRepositoryEmployeeBadge repositoryEmployeeBadge;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = false)
    @Override
    public EmployeeBadge assignBadgeToEmployee(EmployeeBadge employeeBadge) {
        loggingService.logInfo("Iniciando asignación de Badge con ID: {} a Employee con ID: {}", 
                employeeBadge.getBadge().getId(), employeeBadge.getEmployee().getId());
        try {
            if (!repositoryUser.existsById(employeeBadge.getEmployee().getId())) {
                loggingService.logError("Usuario con ID {} no encontrado", employeeBadge.getEmployee().getId());
                throw new UserNotFoundException(employeeBadge.getEmployee().getId());
            }

            if (!repositoryUser.findById(employeeBadge.getEmployee().getId()).get().isEmployee()) {
                loggingService.logError("El usuario con ID {} no tiene rol EMPLOYEE", employeeBadge.getEmployee().getId());
                throw new InvalidRoleException("El usuario no tiene rol de EMPLOYEE");
            }

            if (!repositoryBadge.existsById(employeeBadge.getBadge().getId())) {
                loggingService.logError("Badge con ID {} no encontrado", employeeBadge.getBadge().getId());
                throw new BadgeNotFoundException("Insignia no encontrada");
            }

            List<EmployeeBadge> employeeBadges = repositoryEmployeeBadge.findAll();
            for (EmployeeBadge emplBad : employeeBadges) {
                if (emplBad.getEmployee().getId().equals(employeeBadge.getEmployee().getId()) &&
                        emplBad.getBadge().getId().equals(employeeBadge.getBadge().getId())) {
                    loggingService.logError("Badge con ID {} ya está asignado al Employee con ID {}", 
                            employeeBadge.getBadge().getId(), employeeBadge.getEmployee().getId());
                    throw new EmployeeBadgeAlreadyRegisteredException(employeeBadge.getEmployee().getId(), 
                            employeeBadge.getBadge().getId());
                }
            }

            loggingService.logDebug("Obteniendo entidades para EmployeeBadge: employeeId={}, badgeId={}", 
                    employeeBadge.getEmployee().getId(), employeeBadge.getBadge().getId());
            employeeBadge.setEmployee((Employee) repositoryUser.findById(employeeBadge.getEmployee().getId()).get());
            employeeBadge.setBadge(repositoryBadge.findById(employeeBadge.getBadge().getId()).get());

            EmployeeBadge savedEmployeeBadge = repositoryEmployeeBadge.save(employeeBadge);
            loggingService.logInfo("Badge con ID: {} asignado exitosamente a Employee con ID: {}, EmployeeBadge ID: {}", 
                    savedEmployeeBadge.getBadge().getId(), savedEmployeeBadge.getEmployee().getId(), savedEmployeeBadge.getId());
            return savedEmployeeBadge;
        } catch (Exception e) {
            loggingService.logError("Error al asignar Badge con ID {} a Employee con ID {}: {}", 
                    employeeBadge.getBadge().getId(), employeeBadge.getEmployee().getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<EmployeeBadge> findById(Long id) {
        loggingService.logInfo("Obteniendo EmployeeBadge con ID: {}", id);
        try {
            if (!repositoryEmployeeBadge.existsById(id)) {
                loggingService.logError("EmployeeBadge con ID {} no encontrado", id);
                throw new EmployeeBadgeNotFoundException(id);
            }
            Optional<EmployeeBadge> employeeBadge = repositoryEmployeeBadge.findById(id);
            loggingService.logInfo("EmployeeBadge ID {} obtenido exitosamente", id);
            return employeeBadge;
        } catch (Exception e) {
            loggingService.logError("Error al obtener EmployeeBadge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeBadge> findAll() {
        loggingService.logInfo("Obteniendo todos los EmployeeBadges");
        try {
            List<EmployeeBadge> employeeBadges = repositoryEmployeeBadge.findAll();
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

    @Transactional(readOnly = false)
    @Override
    public Optional<EmployeeBadge> delete(Long id) {
        loggingService.logInfo("Iniciando eliminación de EmployeeBadge con ID: {}", id);
        try {
            if (!repositoryEmployeeBadge.existsById(id)) {
                loggingService.logError("EmployeeBadge con ID {} no encontrado", id);
                throw new EmployeeBadgeNotFoundException(id);
            }
            Optional<EmployeeBadge> deletedEmployeeBadge = repositoryEmployeeBadge.delete(id);
            loggingService.logInfo("EmployeeBadge ID {} eliminado exitosamente", id);
            return deletedEmployeeBadge;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar EmployeeBadge ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}