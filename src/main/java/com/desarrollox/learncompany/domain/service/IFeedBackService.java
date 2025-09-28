package com.desarrollox.learncompany.domain.service;

import java.util.List;
import com.desarrollox.learncompany.domain.model.FeedBack;

public interface IFeedBackService {
    List<FeedBack> getFeedBack(Long AssessmentTemplateId);
}
