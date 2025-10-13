package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryIncription;
import com.desarrollox.learncompany.domain.model.Inscription;
import com.desarrollox.learncompany.persistence.mapper.InscriptionMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryInscription;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryInscription implements IRepositoryIncription{
    
    private final JpaRepositoryInscription jpaRepositoryInscription;
    private final InscriptionMapper inscriptionMapper;
    
    @Override
    public Inscription save(Inscription inscription) {
        return inscriptionMapper.toDomain(jpaRepositoryInscription.save(inscriptionMapper.toEntity(inscription)));
    }

    @Override
    public Optional<Inscription> findById(Long id) {
        return jpaRepositoryInscription.findById(id).map(inscriptionMapper::toDomain);
    }

    @Override
    public Optional<Inscription> delete(Long id) {
        return jpaRepositoryInscription.findById(id).map(inscripcionEntity -> {
                jpaRepositoryInscription.delete(inscripcionEntity);
                return inscriptionMapper.toDomain(inscripcionEntity);
        });
    }

    @Override
    public List<Inscription> findInscriptionsByEmployeeId(Long employeeId) {
       return jpaRepositoryInscription.findInscriptionsByEmployeeId(employeeId).stream().map(inscriptionMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Inscription> findInscriptionsByCourseId(Long courseId) {
        return jpaRepositoryInscription.findInscriptionsByCourseId(courseId).stream().map(inscriptionMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Inscription> findAll() {
        return jpaRepositoryInscription.findAll()
                .stream().map(inscriptionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositoryInscription.existsById(id);
    }

}