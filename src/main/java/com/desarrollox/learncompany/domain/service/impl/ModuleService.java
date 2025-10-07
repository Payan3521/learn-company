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
    
        // Obtener el curso existente
        Course course = repositoryCourse.findById(module.getCourse().getId())
            .orElseThrow(() -> new CourseNotFoundException(module.getCourse().getId()));
        module.setCourse(course);
        
        // Guardar el módulo primero para obtener ID
        Module savedModule = repositoryModule.save(module);
        
        // Establecer las relaciones bidireccionales y guardar AssessmentTemplates
        if (module.getAssessmentTemplate() != null) {
            for (AssessmentTemplate assessmentTemplate : module.getAssessmentTemplate()) {
                // Establecer la relación con el módulo
                assessmentTemplate.setModule(savedModule);
                
                // Establecer relaciones bidireccionales con Questions
                if (assessmentTemplate.getQuestions() != null) {
                    for (Question question : assessmentTemplate.getQuestions()) {
                        question.setAssessmentTemplate(assessmentTemplate);
                    }
                }
            }
            
            // Guardar todo el árbol de entidades
            savedModule = repositoryModule.save(savedModule);
        }
        
        return savedModule;
    }

    @Override
    public Optional<Module> getModuleById(Long id) {
        return repositoryModule.findById(id);
    }

    @Override
    public List<Module> getAllModules() {
        return repositoryModule.findAll();
    }

    @Override
    public Optional<Module> deleteModule(Long id) {
        return repositoryModule.delete(id);
    }

    @Override
    public List<Module> getModulesByCourseId(Long courseId) {
        return repositoryModule.findModulesByCourseId(courseId);
    }

    
    
}
