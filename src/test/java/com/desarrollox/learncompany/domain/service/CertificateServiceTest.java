package com.desarrollox.learncompany.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCertificate;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.CertificateNotFoundException;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Certificate;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.domain.service.impl.CertificateService;

@ExtendWith(MockitoExtension.class)
class CertificateServiceTest {
    
    @Mock
    private IRepositoryCertificate repositoryCertificate;

    @Mock
    private IRepositoryUser repositoryUser;
    
    @Mock
    private IRepositoryCourse repositoryCourse;
    
    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private CertificateService certificateService;

    private Certificate certificate;
    private Course course;
    private Employee employee;
    private List<Certificate> certificates;

    @BeforeEach
    void setUp(){

        employee = new Employee();
        employee.setId(1L);
        employee.setRole(Role.EMPLOYEE);


        course = new Course();
        course.setId(1L);

        certificate = new Certificate();
        certificate.setId(1L);
        certificate.setEmployee(employee);
        certificate.setCourse(course);

        certificates = new ArrayList<>();
        certificates.add(certificate);  
    }

    @Test //201
    void createCertificate(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repositoryCourse.findById(anyLong())).thenReturn(Optional.of(course));
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employee));
        when(repositoryCertificate.save(any(Certificate.class)))
            .thenReturn(certificate);
        

        Certificate result = certificateService.createCertificate(certificate);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser, times(2)).findById(anyLong());
        verify(repositoryCourse).findById(anyLong());
        verify(repositoryCertificate).save(any(Certificate.class));
    }

    @Test //404
    void createCertificate_CourseNotFound(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(false);

        CourseNotFoundException thrown = assertThrows(
            CourseNotFoundException.class, 
            () -> certificateService.createCertificate(certificate));

        assertNotNull(thrown);
        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryCertificate, never()).save(any(Certificate.class));
    }

    @Test //404
    void createCertificate_UserNotFound(){
        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.existsById(anyLong())).thenReturn(false);

        UserNotFoundException thrown = assertThrows(
            UserNotFoundException.class,
            () -> certificateService.createCertificate(certificate));

        assertNotNull(thrown);
        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryCertificate, never()).save(any(Certificate.class));
    }

    @Test //409
    void createCertificate_InvalidRole(){

        Employee employePrueba = new Employee();
        employePrueba.setId(2L);
        employePrueba.setRole(Role.INSTRUCTOR);

        when(repositoryCourse.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryUser.findById(anyLong())).thenReturn(Optional.of(employePrueba));

        InvalidRoleException thrown = assertThrows(InvalidRoleException.class,
            () -> certificateService.createCertificate(certificate));

        assertNotNull(thrown);
        verify(repositoryCourse).existsById(anyLong());
        verify(repositoryUser).existsById(anyLong());
        verify(repositoryUser).findById(anyLong());
        verify(repositoryCertificate, never()).save(any(Certificate.class));
    }

    @Test
    void getCertificateById_success(){
        when(repositoryCertificate.existsById(anyLong())).thenReturn(true);
        when(repositoryCertificate.findById(anyLong())).thenReturn(Optional.of(certificate));

        Optional<Certificate> result = certificateService.getCertificateById(1L);

        assertTrue(result.isPresent());
        assertEquals(certificate, result.get());
        assertEquals(certificate.getId(), result.get().getId());

        verify(repositoryCertificate).existsById(anyLong());
        verify(repositoryCertificate).findById(anyLong());
    }

    @Test
    void getCerificateById_CertificateNotFound(){
        when(repositoryCertificate.existsById(anyLong())).thenReturn(false);
        CertificateNotFoundException thrown = assertThrows(
            CertificateNotFoundException.class, 
            () -> certificateService.getCertificateById(1L)
        );

        assertNotNull(thrown);
        verify(repositoryCertificate).existsById(anyLong());
        verify(repositoryCertificate, never()).findById(anyLong());
    }

    @Test
    void getAllCertificates_success(){

        when(repositoryCertificate.findAll()).thenReturn(certificates);

        List<Certificate> result = certificateService.getAllCertificates();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(certificates, result);

        verify(repositoryCertificate).findAll();

    }

    @Test
    void getAllCertificates_isEmpty(){

        when(repositoryCertificate.findAll()).thenReturn(new ArrayList<>());

        List<Certificate> result = certificateService.getAllCertificates();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repositoryCertificate).findAll();
    }

    @Test
    void getCertificateByUserId_success(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryCertificate.findCertificatesByUserId(anyLong())).thenReturn(certificates);

        List<Certificate> result = certificateService.getCertificatesByUserId(1L);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(certificates, result);

        verify(repositoryUser).existsById(anyLong());
        verify(repositoryCertificate).findCertificatesByUserId(anyLong());
    }

    @Test
    void getCertificateByUserId_notFound(){
        
        when(repositoryCertificate.existsById(anyLong())).thenReturn(false);

        CertificateNotFoundException thrown = assertThrows(
            CertificateNotFoundException.class,
            () -> certificateService.getCertificateById(1L)
        );

        assertNotNull(thrown);

        verify(repositoryCertificate).existsById(anyLong());
        verify(repositoryCertificate, never()).findById(anyLong());
    }

    @Test
    void getCertificateByUserId_isEmpty(){
        when(repositoryUser.existsById(anyLong())).thenReturn(true);
        when(repositoryCertificate.findCertificatesByUserId(anyLong())).thenReturn(new ArrayList<>());

        List<Certificate> result = certificateService.getCertificatesByUserId(1L);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repositoryCertificate).findCertificatesByUserId(anyLong());
    }
}
