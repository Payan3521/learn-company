package com.desarrollox.learncompany.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import com.desarrollox.learncompany.domain.model.Certificate;
import com.desarrollox.learncompany.persistence.entity.CertificateEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {EmployeeMapper.class, CourseMapper.class})
public interface CertificateMapper {
    
    Certificate toDomain(CertificateEntity entity);
    CertificateEntity toEntity(Certificate domain);
}