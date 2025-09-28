package com.desarrollox.learncompany.domain.service;

import java.util.List;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;
import com.desarrollox.learncompany.domain.model.FeedBack;

public interface IAssessmentTemplateService {

    List<AssessmentTemplate> getAssessmentsByModuleId(Long moduleId);

    FeedBack getFeedbackById(Long id);
}