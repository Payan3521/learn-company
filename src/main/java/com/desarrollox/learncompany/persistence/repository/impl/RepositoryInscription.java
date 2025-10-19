package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryInscription; // Corregido el nombre de la interfaz
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.persistence.mapper.InscriptionMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryInscription;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryInscription implements IRepositoryInscription { // Corregido el nombre de la interfaz

    private final JpaRepositoryInscription jpaRepositoryInscription;
    private final InscriptionMapper inscriptionMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public Inscription save(Inscription inscription) {
        loggingService.logInfo("Iniciando guardado de Inscription para employeeId: {}, courseId: {}", 
                inscription.getEmployee().getId() != null ? inscription.getEmployee().getId() : "null",
                inscription.getCourse().getId() != null ? inscription.getCourse().getId() : "null");
        try {
            Inscription savedInscription = inscriptionMapper.toDomain(
                    jpaRepositoryInscription.save(inscriptionMapper.toEntity(inscription))
            );
            loggingService.logInfo("Inscription guardada exitosamente con ID: {}, employeeId: {}, courseId: {}", 
                    savedInscription.getId(), 
                    savedInscription.getEmployee().getId(), 
                    savedInscription.getCourse().getId());
            return savedInscription;
        } catch (Exception e) {
            loggingService.logError("Error al guardar Inscription para employeeId: {}, courseId: {}: {}", 
                    inscription.getEmployee().getId() != null ? inscription.getEmployee().getId() : "null",
                    inscription.getCourse().getId() != null ? inscription.getCourse().getId() : "null",
                    e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Inscription> findById(Long id) {
        loggingService.logInfo("Obteniendo Inscription con ID: {}", id);
        try {
            Optional<Inscription> inscription = jpaRepositoryInscription.findById(id)
                    .map(inscriptionMapper::toDomain);
            if (inscription.isPresent()) {
                loggingService.logInfo("Inscription ID {} obtenida exitosamente", id);
            } else {
                loggingService.logWarning("Inscription con ID {} no encontrada", id);
            }
            return inscription;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Inscription ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Inscription> delete(Long id) {
        loggingService.logInfo("Iniciando eliminación de Inscription con ID: {}", id);
        try {
            Optional<Inscription> inscription = jpaRepositoryInscription.findById(id)
                    .map(inscriptionEntity -> {
                        jpaRepositoryInscription.delete(inscriptionEntity);
                        Inscription deletedInscription = inscriptionMapper.toDomain(inscriptionEntity);
                        loggingService.logInfo("Inscription ID {} eliminada exitosamente", id);
                        return deletedInscription;
                    });
            if (inscription.isEmpty()) {
                loggingService.logWarning("Inscription con ID {} no encontrada para eliminación", id);
            }
            return inscription;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Inscription ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Inscription> findInscriptionsByEmployeeId(Long employeeId) {
        loggingService.logInfo("Obteniendo Inscriptions para employeeId: {}", employeeId);
        try {
            List<Inscription> inscriptions = jpaRepositoryInscription.findInscriptionsByEmployeeId(employeeId)
                    .stream()
                    .map(inscriptionMapper::toDomain)
                    .collect(Collectors.toList());
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

    @Override
    public List<Inscription> findInscriptionsByCourseId(Long courseId) {
        loggingService.logInfo("Obteniendo Inscriptions para courseId: {}", courseId);
        try {
            List<Inscription> inscriptions = jpaRepositoryInscription.findInscriptionsByCourseId(courseId)
                    .stream()
                    .map(inscriptionMapper::toDomain)
                    .collect(Collectors.toList());
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

    @Override
    public List<Inscription> findAll() {
        loggingService.logInfo("Obteniendo todas las Inscriptions");
        try {
            List<Inscription> inscriptions = jpaRepositoryInscription.findAll()
                    .stream()
                    .map(inscriptionMapper::toDomain)
                    .collect(Collectors.toList());
            if (inscriptions.isEmpty()) {
                loggingService.logWarning("No se encontraron Inscriptions");
            } else {
                loggingService.logInfo("Se encontraron {} Inscriptions", inscriptions.size());
            }
            return inscriptions;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todas las Inscriptions: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de Inscription con ID: {}", id);
        try {
            boolean exists = jpaRepositoryInscription.existsById(id);
            loggingService.logDebug("Inscription con ID {} existe: {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de Inscription ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}