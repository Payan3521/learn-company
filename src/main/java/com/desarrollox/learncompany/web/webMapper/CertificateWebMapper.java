package com.desarrollox.learncompany.web.webMapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.model.Certificate;
import com.desarrollox.learncompany.web.dto.CertificateResponse;

@Component
@Mapper(componentModel = "spring")
public interface CertificateWebMapper {
    CertificateResponse toResponse(Certificate certificate);
    List<CertificateResponse> toResponseList(List<Certificate> certificates);
}