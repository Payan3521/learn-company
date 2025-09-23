package com.desarrollox.learncompany.persistence.mapper;

import com.desarrollox.learncompany.domain.model.Certificate;
import com.desarrollox.learncompany.persistence.entity.CertificateEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, CourseMapper.class})
public interface CertificateMapper {

    CertificateEntity toEntity(Certificate certificate);

    Certificate toDomain(CertificateEntity certificateEntity);
}