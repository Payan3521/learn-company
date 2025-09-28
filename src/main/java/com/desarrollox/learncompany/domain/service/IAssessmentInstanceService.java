package com.desarrollox.learncompany.domain.service;

import java.util.Optional;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;

public interface IAssessmentInstanceService {
    AssessmentInstance createAssessmentInstance(AssessmentInstance instance);
    Optional<AssessmentInstance> getAssessmentInstanceById(Long id);
    Double getGrade(Long id);
    Optional<AssessmentInstance> assignGrade(Long id, Double grade);
}