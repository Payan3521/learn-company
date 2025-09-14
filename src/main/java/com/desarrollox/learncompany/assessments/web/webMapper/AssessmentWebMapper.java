package com.desarrollox.learncompany.assessments.web.webMapper;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.assessments.web.dto.AssessmentResponse;
import com.desarrollox.learncompany.assessments.web.dto.CreateAssessmentInstanceRequest;
import com.desarrollox.learncompany.assessments.web.dto.FeedBackResponse;

@Component
public class AssessmentWebMapper {

    public AssessmentResponse toAssessmentResponse(CreateAssessmentInstanceRequest request, boolean showFeedback) {
        return AssessmentResponse.builder()
            .id(1L)
            .assessmentTemplateId(request.getAssessmentTemplate())
            .employId(request.getEmploy())
            .grade(85.5)
            .status("COMPLETED")
            .feedback(showFeedback ? buildDummyFeedback() : List.of())
            .submittedAt(LocalDateTime.now())
            .build();
    }

    public AssessmentResponse toAssessmentResponse(Long id, boolean showFeedback) {
        return AssessmentResponse.builder()
            .id(id)
            .assessmentTemplateId(1L)
            .employId(1L)
            .grade(90.0)
            .status("COMPLETED")
            .feedback(showFeedback ? buildDummyFeedback() : List.of())
            .submittedAt(LocalDateTime.now())
            .build();
    }

    private List<FeedBackResponse> buildDummyFeedback() {
        return List.of(
            new FeedBackResponse(1L, 101L, "What is a microservice?", "A pattern of architecture", "A database"),
            new FeedBackResponse(2L, 102L, "Which keyword is used in Java to inherit?", "extends", "implements")
        );
    }
}