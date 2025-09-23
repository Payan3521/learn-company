package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.Instructor;
import com.desarrollox.learncompany.persistence.entity.InstructorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, CourseMapper.class})
public interface InstructorMapper {

    InstructorEntity toInstructorEntity(Instructor instructor);

    Instructor toInstructor(InstructorEntity instructorEntity);
}