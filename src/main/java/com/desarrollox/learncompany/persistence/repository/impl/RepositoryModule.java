package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.persistence.entity.ModuleEntity;
import com.desarrollox.learncompany.persistence.mapper.ModuleMapper;
import com.desarrollox.learncompany.persistence.mapper.AssessmentTemplateMapper;
import com.desarrollox.learncompany.persistence.mapper.QuestionMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryModule;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryModule;
import com.desarrollox.learncompany.domain.model.Module;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryModule implements IRepositoryModule{
    
    private final JpaRepositoryModule jpaRepositoryModule;
    private final ModuleMapper moduleMapper;
    private final AssessmentTemplateMapper assessmentTemplateMapper;
    private final QuestionMapper questionMapper;

    @Override
    public Module save(Module module) {
        ModuleEntity entity = moduleMapper.toEntity(module);
        
        // Mapear AssessmentTemplates manualmente
        if (module.getAssessmentTemplate() != null) {
            entity.setAssessmentTemplate(
                module.getAssessmentTemplate().stream()
                    .map(assessmentTemplate -> {
                        var assessmentTemplateEntity = assessmentTemplateMapper.toEntity(assessmentTemplate);
                        assessmentTemplateEntity.setModule(entity);
                        
                        // Mapear Questions manualmente
                        if (assessmentTemplate.getQuestions() != null) {
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
        return mapToDomainWithRelations(savedEntity);
    }

    @Override
    public Optional<Module> findById(Long id) {
        return jpaRepositoryModule.findById(id)
                .map(this::mapToDomainWithRelations);
    }

    @Override
    public List<Module> findAll() {
        return jpaRepositoryModule.findAll()
                .stream().map(this::mapToDomainWithRelations)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Module> delete(Long id) {
        return jpaRepositoryModule.findById(id).map(moduleEntity -> {
            jpaRepositoryModule.delete(moduleEntity);
            return mapToDomainWithRelations(moduleEntity);
        });
    }

    
    @Override
    public List<Module> findModulesByCourseId(Long courseId) {
        return jpaRepositoryModule.findByCourseId(courseId)
                .stream()
                .map(this::mapToDomainWithRelations)
                .collect(Collectors.toList());
    }
    
    private Module mapToDomainWithRelations(ModuleEntity entity) {
        Module module = moduleMapper.toDomain(entity);
        
        // Mapear AssessmentTemplates con Questions
        if (entity.getAssessmentTemplate() != null) {
            module.setAssessmentTemplate(
                entity.getAssessmentTemplate().stream()
                    .map(assessmentTemplateEntity -> {
                        var assessmentTemplate = assessmentTemplateMapper.toDomain(assessmentTemplateEntity);
                        assessmentTemplate.setModule(module);
                        
                        // Mapear Questions manualmente
                        if (assessmentTemplateEntity.getQuestions() != null) {
                            assessmentTemplate.setQuestions(
                                assessmentTemplateEntity.getQuestions().stream()
                                    .map(questionEntity -> {
                                        var question = questionMapper.toDomain(questionEntity);
                                        question.setAssessmentTemplate(assessmentTemplate);
                                        return question;
                                    })
                                    .collect(Collectors.toList())
                            );
                        }
                        
                        return assessmentTemplate;
                    })
                    .collect(Collectors.toList())
            );
        }
        
        return module;
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositoryModule.existsById(id);
    }
}