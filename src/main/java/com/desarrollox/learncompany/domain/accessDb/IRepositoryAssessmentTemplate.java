package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;

public interface IRepositoryAssessmentTemplate {
    List<AssessmentTemplate> findAssessmentsByModuleId(Long moduleId);
}