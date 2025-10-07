package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;

public interface IRepositoryAssessmentTemplate {
    List<AssessmentTemplate> findAssessmentsByModuleId(Long moduleId);
    boolean existsById(Long id);
    Optional<AssessmentTemplate> findById(Long id);
}