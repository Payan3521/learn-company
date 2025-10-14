package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Statistic;
import com.desarrollox.learncompany.web.dto.StatisticResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StatisticWebMapper {

    @Mapping(target = "topCourseId", source = "courseTop.id")
    @Mapping(target = "topCourseName", source = "courseTop.title")
    @Mapping(target = "lessCourseId", source = "courseLess.id")
    @Mapping(target = "lessCourseName", source = "courseLess.title")
    StatisticResponse domainToResponse(Statistic statistic);
}