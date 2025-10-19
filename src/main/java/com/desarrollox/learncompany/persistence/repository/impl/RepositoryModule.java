package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.persistence.entity.ModuleEntity;
import com.desarrollox.learncompany.persistence.mapper.ModuleMapper;
import com.desarrollox.learncompany.persistence.mapper.AssessmentTemplateMapper;
import com.desarrollox.learncompany.persistence.mapper.MapperModuleWithRelations;
import com.desarrollox.learncompany.persistence.mapper.QuestionMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryModule;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryModule;
import com.desarrollox.learncompany.domain.model.Module;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryModule implements IRepositoryModule {

    private final JpaRepositoryModule jpaRepositoryModule;
    private final ModuleMapper moduleMapper;
    private final AssessmentTemplateMapper assessmentTemplateMapper;
    private final QuestionMapper questionMapper;
    private final MapperModuleWithRelations mapperModuleWithRelations;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public Module save(Module module) {
        loggingService.logInfo("Iniciando guardado de Module con nombre: {}", truncateName(module.getTitle()));
        try {
            ModuleEntity entity = moduleMapper.toEntity(module);

            // Mapear AssessmentTemplates manualmente
            if (module.getAssessmentTemplate() != null) {
                loggingService.logDebug("Mapeando {} AssessmentTemplates para Module: {}", 
                        module.getAssessmentTemplate().size(), truncateName(module.getTitle()));
                entity.setAssessmentTemplate(
                        module.getAssessmentTemplate().stream()
                                .map(assessmentTemplate -> {
                                    var assessmentTemplateEntity = assessmentTemplateMapper.toEntity(assessmentTemplate);
                                    assessmentTemplateEntity.setModule(entity);

                                    // Mapear Questions manualmente
                                    if (assessmentTemplate.getQuestions() != null) {
                                        loggingService.logDebug("Mapeando {} Questions para AssessmentTemplate en Module: {}", 
                                                assessmentTemplate.getQuestions().size(), truncateName(module.getTitle()));
                                        assessmentTemplateEntity.setQuestions(
                                                assessmentTemplate.getQuestions().stream()
                                                        .map(question -> {
                                                            var questionEntity = questionMapper.toEntity(question);
                                                            questionEntity.setAssessmentTemplate(assessmentTemplateEntity);
                                                            return questionEntity;
                                                        })
                                                        .collect(Collectors.toList())
                                        );
                                    }

                                    return assessmentTemplateEntity;
                                })
                                .collect(Collectors.toList())
                );
            }

            ModuleEntity savedEntity = jpaRepositoryModule.save(entity);
            Module savedModule = mapperModuleWithRelations.mapToDomainWithRelations(savedEntity);
            loggingService.logInfo("Module guardado exitosamente con ID: {} y nombre: {}", 
                    savedModule.getId(), truncateName(savedModule.getTitle()));
            return savedModule;
        } catch (Exception e) {
            loggingService.logError("Error al guardar Module con nombre {}: {}", 
                    truncateName(module.getTitle()), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Module> findById(Long id) {
        loggingService.logInfo("Obteniendo Module con ID: {}", id);
        try {
            Optional<Module> module = jpaRepositoryModule.findById(id)
                    .map(mapperModuleWithRelations::mapToDomainWithRelations);
            if (module.isPresent()) {
                loggingService.logInfo("Module ID {} obtenido exitosamente", id);
            } else {
                loggingService.logWarning("Module con ID {} no encontrado", id);
            }
            return module;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Module ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Module> findAll() {
        loggingService.logInfo("Obteniendo todos los Modules");
        try {
            List<Module> modules = jpaRepositoryModule.findAll()
                    .stream()
                    .map(mapperModuleWithRelations::mapToDomainWithRelations)
                    .collect(Collectors.toList());
            if (modules.isEmpty()) {
                loggingService.logWarning("No se encontraron Modules");
            } else {
                loggingService.logInfo("Se encontraron {} Modules", modules.size());
            }
            return modules;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todos los Modules: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Module> delete(Long id) {
        loggingService.logInfo("Iniciando eliminación de Module con ID: {}", id);
        try {
            Optional<Module> module = jpaRepositoryModule.findById(id)
                    .map(moduleEntity -> {
                        jpaRepositoryModule.delete(moduleEntity);
                        Module deletedModule = mapperModuleWithRelations.mapToDomainWithRelations(moduleEntity);
                        loggingService.logInfo("Module ID {} eliminado exitosamente", id);
                        return deletedModule;
                    });
            if (module.isEmpty()) {
                loggingService.logWarning("Module con ID {} no encontrado para eliminación", id);
            }
            return module;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Module ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Module> findModulesByCourseId(Long courseId) {
        loggingService.logInfo("Obteniendo Modules para courseId: {}", courseId);
        try {
            List<Module> modules = jpaRepositoryModule.findByCourseId(courseId)
                    .stream()
                    .map(mapperModuleWithRelations::mapToDomainWithRelations)
                    .collect(Collectors.toList());
            if (modules.isEmpty()) {
                loggingService.logWarning("No se encontraron Modules para courseId: {}", courseId);
            } else {
                loggingService.logInfo("Se encontraron {} Modules para courseId: {}", modules.size(), courseId);
            }
            return modules;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Modules para courseId {}: {}", courseId, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de Module con ID: {}", id);
        try {
            boolean exists = jpaRepositoryModule.existsById(id);
            loggingService.logDebug("Module con ID {} existe: {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de Module ID {}: {}", id, e.getMessage(), e);
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