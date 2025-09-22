package com.desarrollox.learncompany.web.webMapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.web.dto.BadgeResponse;

@Component
@Mapper(componentModel = "spring")
public interface BadgeWebMapper {
    BadgeResponse toResponse(Badge badge);
    List<BadgeResponse> toResponseList(List<Badge> badges);
}