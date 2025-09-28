package com.desarrollox.learncompany.domain.accessDb;

import java.util.Optional;
import com.desarrollox.learncompany.domain.model.AssessmentInstance;

public interface IRepositoryAssessmentInstance {
    AssessmentInstance createAssessmentInstance(AssessmentInstance instance);
    Optional<AssessmentInstance> getAssessmentInstanceById(Long id);
    Optional<AssessmentInstance> assignGrade(Long id, Double grade);
}