package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.EmployeeBadge;

public interface IRepositoryEmployeeBadge {

    EmployeeBadge save(EmployeeBadge employeeBadge);
    Optional<EmployeeBadge> findById(Long id);
    List<EmployeeBadge> findAll();
    Optional<EmployeeBadge> delete(Long id);
    boolean existsById(Long id);
}
