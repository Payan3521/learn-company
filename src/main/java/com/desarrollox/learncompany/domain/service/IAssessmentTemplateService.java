package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.domain.model.FeedBack;

public interface IAssessmentTemplateService {

    List<AssessmentTemplate> getAssessmentsByModuleId(Long moduleId);
    Optional<AssessmentTemplate> findById(Long id);
    FeedBack getFeedbackById(Long id);
}