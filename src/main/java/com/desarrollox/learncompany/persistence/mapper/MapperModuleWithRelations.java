package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.persistence.entity.ModuleEntity;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.model.Module;


@RequiredArgsConstructor
@Component
public class MapperModuleWithRelations {

    private final ModuleMapper moduleMapper;
    private final AssessmentTemplateMapper assessmentTemplateMapper;
    private final QuestionMapper questionMapper;

    public Module mapToDomainWithRelations(ModuleEntity entity) {
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
}