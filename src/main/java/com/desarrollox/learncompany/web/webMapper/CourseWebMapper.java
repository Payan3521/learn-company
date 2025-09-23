package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.web.dto.CourseRequest;
import com.desarrollox.learncompany.web.dto.CourseResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ModuleWebMapper.class, InscriptionWebMapper.class, 
                SeasonWebMapper.class, UserWebMapper.class})
public interface CourseWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modules", ignore = true)
    @Mapping(target = "inscriptions", ignore = true)
    @Mapping(target = "season.id", source = "seasonId")
    @Mapping(target = "season.name", ignore = true)
    @Mapping(target = "season.duration", ignore = true)
    @Mapping(target = "season.courses", ignore = true)
    @Mapping(target = "instructor.id", source = "instructorId")
    @Mapping(target = "instructor.email", ignore = true)
    @Mapping(target = "instructor.password", ignore = true)
    @Mapping(target = "instructor.name", ignore = true)
    @Mapping(target = "instructor.lastname", ignore = true)
    @Mapping(target = "instructor.status", ignore = true)
    @Mapping(target = "instructor.role", ignore = true)
    @Mapping(target = "instructor.department", ignore = true)
    @Mapping(target = "instructor.urlPhoto", ignore = true)
    @Mapping(target = "instructor.specialty", ignore = true)
    @Mapping(target = "instructor.biography", ignore = true)
    @Mapping(target = "instructor.courses", ignore = true)
    Course requestToDomain(CourseRequest request);
    
    CourseResponse domainToResponse(Course domain);
}