package com.desarrollox.learncompany.persistence.mapper;

import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;
import com.desarrollox.learncompany.persistence.entity.AssessmentInstanceEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class MapperAssessmentInstanceWithRelations {

    private final AssessmentInstanceMapper assessmentInstanceMapper;
    private final AnswerMapper answerMapper;
    private final QuestionMapper questionMapper;

    public AssessmentInstance mapToDomainWithRelations(AssessmentInstanceEntity entity) {
        AssessmentInstance assessmentInstance = assessmentInstanceMapper.toDomain(entity);
        
        // Mapear Answers con Questions
        if (entity.getAnswers() != null) {
            assessmentInstance.setAnswers(
                entity.getAnswers().stream()
                    .map(answerEntity -> {
                        var answer = answerMapper.toDomain(answerEntity);
                        answer.setAssessmentInstance(assessmentInstance);
                        
                        // Mapear Question manualmente
                        if (answerEntity.getQuestion() != null) {
                            var question = questionMapper.toDomain(answerEntity.getQuestion());
                            answer.setQuestion(question);
                        }
                        
                        return answer;
                    })
                    .collect(Collectors.toList())
            );
        }
        
        return assessmentInstance;
    }
}