package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryBadge;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryEmployeeBadge;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.BadgeNotFoundException;
import com.desarrollox.learncompany.domain.exception.EmployeeBadgeNotFoundException;
import com.desarrollox.learncompany.domain.exception.InvalidRoleException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.EmployeeBadge;
import com.desarrollox.learncompany.domain.service.IEmployeeBadgeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeBadgeService implements IEmployeeBadgeService{

    private final IRepositoryBadge repositoryBadge;
    private final IRepositoryUser repositoryUser;
    private final IRepositoryEmployeeBadge repositoryEmployeeBadge;

    @Transactional(readOnly = false)
    @Override
    public EmployeeBadge assignBadgeToEmployee(EmployeeBadge employeeBadge) {

        if (!repositoryUser.existsById(employeeBadge.getEmployee().getId())) {
            throw new UserNotFoundException(employeeBadge.getEmployee().getId());
        }

        if (!repositoryUser.findById(employeeBadge.getEmployee().getId()).get().isEmployee()) {
            throw new InvalidRoleException("El usuario no tiene rol de EMPLOYEE");
        }

        if (!repositoryBadge.existsById(employeeBadge.getBadge().getId())) {
            throw new BadgeNotFoundException("Insignia no encontrada");
        }

        employeeBadge.setEmployee((Employee)repositoryUser.findById(employeeBadge.getEmployee().getId()).get());
        employeeBadge.setBadge(repositoryBadge.findById(employeeBadge.getBadge().getId()).get());

        return repositoryEmployeeBadge.save(employeeBadge);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<EmployeeBadge> findById(Long id) {
        if(!repositoryEmployeeBadge.existsById(id)){
            throw new EmployeeBadgeNotFoundException(id);
        }
        return repositoryEmployeeBadge.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployeeBadge> findAll() {
        return repositoryEmployeeBadge.findAll();
    }

    @Transactional(readOnly = false)
    @Override
    public Optional<EmployeeBadge> delete(Long id) {
        if(!repositoryEmployeeBadge.existsById(id)){
            throw new EmployeeBadgeNotFoundException(id);
        }
        return repositoryEmployeeBadge.delete(id);
    }
}