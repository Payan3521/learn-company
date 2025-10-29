package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.domain.model.Question;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryModule;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.ModuleNotFoundException;
import com.desarrollox.learncompany.domain.service.IModuleService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleService implements IModuleService {

    private final IRepositoryModule repositoryModule;
    private final IRepositoryCourse repositoryCourse;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = false)
    @Override
    public Module createModule(Module module) {
        loggingService.logInfo("Iniciando creación de Module para courseId: {}", module.getCourse().getId());
        try {
            if (!repositoryCourse.existsById(module.getCourse().getId())) {
                loggingService.logError("Curso con ID {} no encontrado", module.getCourse().getId());
                throw new CourseNotFoundException(module.getCourse().getId());
            }

            loggingService.logDebug("Obteniendo Course con ID: {}", module.getCourse().getId());
            Course course = repositoryCourse.findById(module.getCourse().getId())
                    .orElseThrow(() -> {
                        loggingService.logError("Curso con ID {} no encontrado en búsqueda", module.getCourse().getId());
                        return new CourseNotFoundException(module.getCourse().getId());
                    });
            module.setCourse(course);

            // Establecer las relaciones bidireccionales
            if (module.getAssessmentTemplate() != null) {
                loggingService.logDebug("Procesando {} AssessmentTemplates para el Module", module.getAssessmentTemplate().size());
                for (AssessmentTemplate assessmentTemplate : module.getAssessmentTemplate()) {
                    assessmentTemplate.setModule(module);
                    if (assessmentTemplate.getQuestions() != null) {
                        loggingService.logDebug("Procesando {} Questions para AssessmentTemplate", assessmentTemplate.getQuestions().size());
                        for (Question question : assessmentTemplate.getQuestions()) {
                            question.setAssessmentTemplate(assessmentTemplate);
                        }
                    }
                }
            }

            Module savedModule = repositoryModule.save(module);
            loggingService.logInfo("Module creado exitosamente con ID: {} para courseId: {}", 
                    savedModule.getId(), savedModule.getCourse().getId());
            return savedModule;
        } catch (Exception e) {
            loggingService.logError("Error al crear Module para courseId {}: {}", 
                    module.getCourse().getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Module> getModuleById(Long id) {
        loggingService.logInfo("Obteniendo Module con ID: {}", id);
        try {
            if (!repositoryModule.existsById(id)) {
                loggingService.logError("Module con ID {} no encontrado", id);
                throw new ModuleNotFoundException(id);
            }
            Optional<Module> module = repositoryModule.findById(id);
            loggingService.logInfo("Module ID {} obtenido exitosamente", id);
            return module;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Module ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Module> getAllModules() {
        loggingService.logInfo("Obteniendo todos los Modules");
        try {
            List<Module> modules = repositoryModule.findAll();
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

    @Transactional(readOnly = false)
    @Override
    public Optional<Module> deleteModule(Long id) {
        loggingService.logInfo("Iniciando eliminación de Module con ID: {}", id);
        try {
            if (!repositoryModule.existsById(id)) {
                loggingService.logError("Module con ID {} no encontrado", id);
                throw new ModuleNotFoundException(id);
            }
            Optional<Module> deletedModule = repositoryModule.delete(id);
            loggingService.logInfo("Module ID {} eliminado exitosamente", id);
            return deletedModule;
        } catch (Exception e) {
            loggingService.logError("Error al eliminar Module ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Module> getModulesByCourseId(Long courseId) {
        loggingService.logInfo("Obteniendo Modules para courseId: {}", courseId);
        try {
            if (!repositoryCourse.existsById(courseId)) {
                loggingService.logError("Curso con ID {} no encontrado", courseId);
                throw new CourseNotFoundException(courseId);
            }
            List<Module> modules = repositoryModule.findModulesByCourseId(courseId);
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
}