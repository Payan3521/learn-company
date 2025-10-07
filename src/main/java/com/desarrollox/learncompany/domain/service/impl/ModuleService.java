package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
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

    @Override
    public Module createModule(Module module) {
        if(!repositoryCourse.existsById(module.getCourse().getId())){
            throw new CourseNotFoundException(module.getCourse().getId());
        }

        //esto podria cambiar a solo una simple validacion
    
        // Obtener el curso existente
        Course course = repositoryCourse.findById(module.getCourse().getId())
            .orElseThrow(() -> new CourseNotFoundException(module.getCourse().getId()));
        module.setCourse(course);
        
        // Establecer las relaciones bidireccionales ANTES de guardar
        if (module.getAssessmentTemplate() != null) {
            for (AssessmentTemplate assessmentTemplate : module.getAssessmentTemplate()) {
                // Establecer la relación con el módulo
                assessmentTemplate.setModule(module);
                
                // Establecer relaciones bidireccionales con Questions
                if (assessmentTemplate.getQuestions() != null) {
                    for (Question question : assessmentTemplate.getQuestions()) {
                        question.setAssessmentTemplate(assessmentTemplate);
                    }
                }
            }
        }
        
        // Guardar el módulo con todas sus relaciones
        return repositoryModule.save(module);
    }

    @Override
    public Optional<Module> getModuleById(Long id) {
        if(!repositoryModule.existsById(id)){
            throw new ModuleNotFoundException(id);
        }
        return repositoryModule.findById(id);
    }

    @Override
    public List<Module> getAllModules() {
        return repositoryModule.findAll();
    }

    @Override
    public Optional<Module> deleteModule(Long id) {
        if(!repositoryModule.existsById(id)){
            throw new ModuleNotFoundException(id);
        }
        return repositoryModule.delete(id);
    }

    @Override
    public List<Module> getModulesByCourseId(Long courseId) {
        return repositoryModule.findModulesByCourseId(courseId);
    }
    
}