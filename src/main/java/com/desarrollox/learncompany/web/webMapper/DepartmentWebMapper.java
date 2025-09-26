package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Department;
import com.desarrollox.learncompany.web.dto.DepartmentRequest;
import com.desarrollox.learncompany.web.dto.DepartmentResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, 
    uses = {CourseWebMapper.class})
public interface DepartmentWebMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Department requestToDomain(DepartmentRequest request);

    
    DepartmentResponse domainToResponse(Department domain);
}