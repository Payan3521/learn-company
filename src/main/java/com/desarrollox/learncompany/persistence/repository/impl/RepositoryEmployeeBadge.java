package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryEmployeeBadge;
import com.desarrollox.learncompany.domain.model.EmployeeBadge;
import com.desarrollox.learncompany.persistence.mapper.EmployeeBadgeMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryEmployeeBadge;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryEmployeeBadge implements IRepositoryEmployeeBadge{

    private final JpaRepositoryEmployeeBadge jpaRepositoryEmployeeBadge;
    private final EmployeeBadgeMapper employeeBadgeMapper;


    @Override
    public EmployeeBadge save(EmployeeBadge employeeBadge) {
        return employeeBadgeMapper.toDomain(jpaRepositoryEmployeeBadge.save(employeeBadgeMapper.toEntity(employeeBadge)));
    }

    @Override
    public Optional<EmployeeBadge> findById(Long id) {
        return jpaRepositoryEmployeeBadge.findById(id).map(employeeBadgeMapper::toDomain);
    }

    @Override
    public List<EmployeeBadge> findAll() {
        return jpaRepositoryEmployeeBadge.findAll().stream().map(employeeBadgeMapper :: toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<EmployeeBadge> delete(Long id) {
        return jpaRepositoryEmployeeBadge.findById(id).map(entity -> {
            jpaRepositoryEmployeeBadge.delete(entity);
            return employeeBadgeMapper.toDomain(entity);
        });
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositoryEmployeeBadge.existsById(id);
    }

}