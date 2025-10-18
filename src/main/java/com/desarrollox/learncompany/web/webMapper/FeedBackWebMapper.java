package com.desarrollox.learncompany.web.webMapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.FeedBack;
import com.desarrollox.learncompany.web.dto.FeedBackResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FeedBackWebMapper {
    FeedBackResponse domainToResponse(FeedBack feedBack);
}