package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryBadge;
import com.desarrollox.learncompany.domain.exception.BadgeNotFoundException;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.service.IBadgeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BadgeService implements IBadgeService{

    private final IRepositoryBadge repositoryBadge;

    @Override
    public Badge createBadge(Badge badge) {
        return repositoryBadge.save(badge);
    }

    @Override
    public Optional<Badge> getBadgeById(Long id) {

        if(repositoryBadge.existsById(id)){
            return repositoryBadge.findById(id);
        }
        throw new BadgeNotFoundException(id);
    }

    @Override
    public List<Badge> getAllBadges() {
        return repositoryBadge.findAll();
    }

    @Override
    public List<Badge> getBadgesByEmployeeId(Long employeeId) {
        return repositoryBadge.findBadgesByEmployeeId(employeeId);
    }

    @Override
    public Optional<Badge> findByName(String name) {
        return repositoryBadge.findBadgeByName(name);
    }

    @Override
    public Badge assignBadgeToEmployee(Employee employee) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'assignBadgeToEmployee'");
    }
    
}