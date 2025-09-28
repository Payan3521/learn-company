package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Inscription;

public interface IInscriptionService {
    Inscription createInscription(Inscription inscription);
    Optional<Inscription> getInscriptionById(Long id);
    Optional<Inscription> deleteInscription(Long id);
    List<Inscription> findByEmployeeId(Long employeeId);
    List<Inscription> findByCourseId(Long courseId);
}