package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryIncription;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.CourseNotFoundException;
import com.desarrollox.learncompany.domain.exception.InscriptionAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.InscriptionNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.domain.service.IInscriptionService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InscriptionService implements IInscriptionService{

    private final IRepositoryIncription repositoryInscription;
    private final IRepositoryUser repositoryUser;
    private final IRepositoryCourse repositoryCourse;

    @Override
    public Inscription createInscription(Inscription inscription) {
        
        if (!repositoryUser.existsById(inscription.getEmployee().getId())) {
            throw new UserNotFoundException(inscription.getEmployee().getId());
        }

        if (!repositoryUser.findById(inscription.getEmployee().getId()).get().isEmployee()) {
            throw new InvalidRoleException("El usuario no tiene rol de EMPLOYEE");
        }

        if (!repositoryCourse.existsById(inscription.getCourse().getId())) {
            throw new CourseNotFoundException(inscription.getCourse().getId());
        }

        List<Inscription> inscriptions = repositoryInscription.findAll();

        for (Inscription ins : inscriptions) {
            if(ins.getEmployee().getId().equals(inscription.getEmployee().getId())){
                throw new InscriptionAlreadyRegisteredException(inscription.getEmployee().getId(), inscription.getCourse().getId());
            }
        }

        inscription.setEmployee((Employee)repositoryUser.findById(inscription.getEmployee().getId()).get());
        inscription.setCourse(repositoryCourse.findById(inscription.getCourse().getId()).get());
        
        return repositoryInscription.save(inscription);  
    }

    @Override
    public Optional<Inscription> getInscriptionById(Long id) {
        if(repositoryInscription.existsById(id)){
            return repositoryInscription.findById(id);
        }
         throw new InscriptionNotFoundException(id);
    }

    @Override
    public Optional<Inscription> deleteInscription(Long id) {
        if(!repositoryInscription.existsById(id)){
            throw new InscriptionNotFoundException(id);
        }
        return repositoryInscription.delete(id);
    }
    

    @Override
    public List<Inscription> findByEmployeeId(Long employeeId) {
        if(!repositoryUser.existsById(employeeId)){
            throw new UserNotFoundException(employeeId);
        }
        return repositoryInscription.findInscriptionsByEmployeeId(employeeId);
    }

    @Override
    public List<Inscription> findByCourseId(Long courseId) {
        if(!repositoryCourse.existsById(courseId)){
            throw new CourseNotFoundException(courseId);
        }
        return repositoryInscription.findInscriptionsByCourseId(courseId);
    }
    
}
