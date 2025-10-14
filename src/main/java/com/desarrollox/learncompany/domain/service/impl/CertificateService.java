package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCertificate;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.CertificateNotFoundException;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Certificate;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.service.ICertificateService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CertificateService implements ICertificateService {

    private final IRepositoryCertificate repositoryCertificate;
    private final IRepositoryUser repositoryUser;
    private final IRepositoryCourse repositoryCourse;

    @Transactional(readOnly = false)
    @Override
    public Certificate createCertificate(Certificate certificate) {
        if(!repositoryCourse.existsById(certificate.getCourse().getId())){
            throw new CourseNotFoundException(certificate.getCourse().getId());
        }
        if(!repositoryUser.existsById(certificate.getEmployee().getId())){
            throw new UserNotFoundException(certificate.getEmployee().getId());
        }
        if(!repositoryUser.findById(certificate.getEmployee().getId()).get().isEmployee()){
            throw new InvalidRoleException("El usuario con ID " + certificate.getEmployee().getId() + " no tiene rol de EMPLOYEE");
        }

        certificate.setCourse(repositoryCourse.findById(certificate.getCourse().getId()).get());
        certificate.setEmployee((Employee)repositoryUser.findById(certificate.getEmployee().getId()).get());
        return repositoryCertificate.save(certificate);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Certificate> getAllCertificates() {
        return repositoryCertificate.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Certificate> getCertificateById(Long id) {

        if(repositoryCertificate.existsById(id)){
            return repositoryCertificate.findById(id);
        }
        
        throw new CertificateNotFoundException(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Certificate> getCertificatesByUserId(Long userId) {
        if(!repositoryUser.existsById(userId)){
            throw new UserNotFoundException(userId);
        }
        return repositoryCertificate.findCertificatesByUserId(userId);
    }
    
}