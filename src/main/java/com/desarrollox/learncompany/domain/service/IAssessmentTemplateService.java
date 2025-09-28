package com.desarrollox.learncompany.domain.service;

import java.util.List;
import com.desarrollox.learncompany.domain.model.AssessmentTemplate;

public interface IAssessmentTemplateService {

    List<AssessmentTemplate> getAssessmentsByModuleId(Long moduleId);
}