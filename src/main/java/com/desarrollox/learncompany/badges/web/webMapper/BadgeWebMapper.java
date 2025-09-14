package com.desarrollox.learncompany.badges.web.webMapper;

import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.badges.web.dto.BadgeResponse;
import com.desarrollox.learncompany.badges.web.dto.CreateBadgeRequest;

@Component
public class BadgeWebMapper {
    
    public BadgeResponse toBadgeResponse(CreateBadgeRequest request) {
        return BadgeResponse.builder()
            .id(1L)
            .name(request.getName())
            .urlIcono(request.getUrlIcono())
            .criteria(request.getCriteria())
            .active(true)
            .build();
    }
    
    public BadgeResponse toBadgeResponse(Long id, String name) {
        return BadgeResponse.builder()
            .id(id)
            .name(name)
            .urlIcono("https://example.com/badge.png")
            .criteria("Standard criteria")
            .active(true)
            .build();
    }
}