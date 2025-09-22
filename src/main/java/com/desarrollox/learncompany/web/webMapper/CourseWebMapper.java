package com.desarrollox.learncompany.web.webMapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.web.dto.CourseResponse;

@Component
@Mapper(componentModel = "spring")
public interface CourseWebMapper {
    CourseResponse toResponse(Course course);
    List<CourseResponse> toResponseList(List<Course> badges);
}