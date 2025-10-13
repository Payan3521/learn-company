package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.EmployeeBadge;

public interface IEmployeeBadgeService {
    EmployeeBadge assignBadgeToEmployee(EmployeeBadge employeeBadge);
    Optional<EmployeeBadge> findById(Long id);
    List<EmployeeBadge> findAll();
    Optional<EmployeeBadge> delete(Long id);
}
