package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryInscription;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.DepartmentIncorrectException;
import com.desarrollox.learncompany.domain.exception.InscriptionAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.InscriptionNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.domain.service.IInscriptionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InscriptionService implements IInscriptionService {

    private final IRepositoryInscription repositoryInscription;
    private final IRepositoryUser repositoryUser;
    private final IRepositoryCourse repositoryCourse;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = false)
    @Override
    public Inscription createInscription(Inscription inscription) {
        loggingService.logInfo("Iniciando creación de Inscription para employeeId: {} y courseId: {}", 
                inscription.getEmployee().getId(), inscription.getCourse().getId());
        try {
            if (!repositoryUser.existsById(inscription.getEmployee().getId())) {
                loggingService.logError("Usuario con ID {} no encontrado", inscription.getEmployee().getId());
                throw new UserNotFoundException(inscription.getEmployee().getId());
            }

            if (!repositoryUser.findById(inscription.getEmployee().getId()).get().isEmployee()) {
                loggingService.logError("El usuario con ID {} no tiene rol EMPLOYEE", inscription.getEmployee().getId());
                throw new InvalidRoleException("El usuario no tiene rol de EMPLOYEE");
            }

            if (!repositoryCourse.existsById(inscription.getCourse().getId())) {
                loggingService.logError("Curso con ID {} no encontrado", inscription.getCourse().getId());
                throw new CourseNotFoundException(inscription.getCourse().getId());
            }

            if (!repositoryCourse.findById(inscription.getCourse().getId()).get().getDepartment().getId()
                    .equals(repositoryUser.findById(inscription.getEmployee().getId()).get().getDepartment().getId())) {
                loggingService.logError("El curso con ID {} no pertenece al departamento del empleado con ID {}", 
                        inscription.getCourse().getId(), inscription.getEmployee().getId());
                throw new DepartmentIncorrectException();
            }

            List<Inscription> inscriptions = repositoryInscription.findAll();
            for (Inscription ins : inscriptions) {
                if (ins.getEmployee().getId().equals(inscription.getEmployee().getId()) &&
                        ins.getCourse().getId().equals(inscription.getCourse().getId())) {
                    loggingService.logError("Inscription ya registrada para employeeId: {} y courseId: {}", 
                            inscription.getEmployee().getId(), inscription.getCourse().getId());
                    throw new InscriptionAlreadyRegisteredException(inscription.getEmployee().getId(), 
                            inscription.getCourse().getId());
                }
            }

            loggingService.logDebug("Obteniendo entidades para Inscription: employeeId={}, courseId={}", 
                    inscription.getEmployee().getId(), inscription.getCourse().getId());
            inscription.setEmployee((Employee) repositoryUser.findById(inscription.getEmployee().getId()).get());
            inscription.setCourse(repositoryCourse.findById(inscription.getCourse().getId()).get());

            Inscription savedInscription = repositoryInscription.save(inscription);
            loggingService.logInfo("Inscription creada exitosamente con ID: {} para employeeId: {} y courseId: {}", 
                    savedInscription.getId(), savedInscription.getEmployee().getId(), savedInscription.getCourse().getId());
            return savedInscription;
        } catch (Exception e) {
            loggingService.logError("Error al crear Inscription para employeeId: {} y courseId: {}: {}", 
                    inscription.getEmployee().getId(), inscription.getCourse().getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Inscription> getInscriptionById(Long id) {
        loggingService.logInfo("Obteniendo Inscription con ID: {}", id);
        try {
            if (!repositoryInscription.existsById(id)) {
                loggingService.logError("Inscription con ID {} no encontrada", id);
                throw new InscriptionNotFoundException(id);
            }
            Optional<Inscription> inscription = repositoryInscription.findById(id);
            loggingService.logInfo("Inscription ID {} obtenida exitosamente", id);
            return inscription;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Inscription ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = false)
    @Override
    public Optional<Inscription> deleteInscription(Long id) {
        loggingService.logInfo("Iniciando eliminación de Inscription con ID: {}", id);
        try {
            if (!repositoryInscription.existsById(id)) {
                loggingService.logError("Inscription con ID {} no encontrada", id);
                throw new InscriptionNotFoundException(id);
            }
            Optional<Inscription> deletedInscription = repositoryInscription.delete(id);
            loggingService.logInfo("Inscription ID {} eliminada exitosamente", id);
            return deletedInscription;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Inscription ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Inscription> findByEmployeeId(Long employeeId) {
        loggingService.logInfo("Obteniendo Inscriptions para employeeId: {}", employeeId);
        try {
            if (!repositoryUser.existsById(employeeId)) {
                loggingService.logError("Usuario con ID {} no encontrado", employeeId);
                throw new UserNotFoundException(employeeId);
            }
            List<Inscription> inscriptions = repositoryInscription.findInscriptionsByEmployeeId(employeeId);
            if (inscriptions.isEmpty()) {
                loggingService.logWarning("No se encontraron Inscriptions para employeeId: {}", employeeId);
            } else {
                loggingService.logInfo("Se encontraron {} Inscriptions para employeeId: {}", inscriptions.size(), employeeId);
            }
            return inscriptions;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Inscriptions para employeeId {}: {}", employeeId, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Inscription> findByCourseId(Long courseId) {
        loggingService.logInfo("Obteniendo Inscriptions para courseId: {}", courseId);
        try {
            if (!repositoryCourse.existsById(courseId)) {
                loggingService.logError("Curso con ID {} no encontrado", courseId);
                throw new CourseNotFoundException(courseId);
            }
            List<Inscription> inscriptions = repositoryInscription.findInscriptionsByCourseId(courseId);
            if (inscriptions.isEmpty()) {
                loggingService.logWarning("No se encontraron Inscriptions para courseId: {}", courseId);
            } else {
                loggingService.logInfo("Se encontraron {} Inscriptions para courseId: {}", inscriptions.size(), courseId);
            }
            return inscriptions;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Inscriptions para courseId {}: {}", courseId, e.getMessage(), e);
            throw e;
        }
    }
}