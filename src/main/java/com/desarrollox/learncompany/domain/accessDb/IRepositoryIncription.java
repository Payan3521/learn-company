package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Inscription;

public interface IRepositoryIncription {
    Inscription save(Inscription inscription);
    Optional<Inscription> findById(Long id);
    Optional<Inscription> delete(Long id);
    List<Inscription> findInscriptionsByEmployeeId(Long employeeId);
    List<Inscription> findInscriptionsByCourseId(Long courseId);
    List<Inscription> findAll();
    boolean existsById(Long id);
}