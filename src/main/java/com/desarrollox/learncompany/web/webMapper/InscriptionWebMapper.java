package com.desarrollox.learncompany.web.webMapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.web.dto.InscriptionResponse;

@Component
@Mapper(componentModel = "spring")
public interface InscriptionWebMapper {
    InscriptionResponse toResponse(Inscription inscription);
    List<InscriptionResponse> toResponseList(List<Inscription> inscriptions);
}