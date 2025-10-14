package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryBadge;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.exception.BadgeAlreadyRegisteredException;
import com.desarrollox.learncompany.domain.exception.BadgeNotFoundException;
import com.desarrollox.learncompany.domain.exception.UserNotFoundException;
import com.desarrollox.learncompany.domain.model.Badge;
import com.desarrollox.learncompany.domain.service.IBadgeService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BadgeService implements IBadgeService{

    private final IRepositoryBadge repositoryBadge;
    private final IRepositoryUser repositoryUser;

    @Transactional(readOnly = false)
    @Override
    public Badge createBadge(Badge badge) {
        if(repositoryBadge.findBadgeByName(badge.getName()).isPresent()){
            throw new BadgeAlreadyRegisteredException(badge.getName());
        }
        return repositoryBadge.save(badge);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Badge> getBadgeById(Long id) {

        if(repositoryBadge.existsById(id)){
            return repositoryBadge.findById(id);
        }
        throw new BadgeNotFoundException(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Badge> getAllBadges() {
        return repositoryBadge.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Badge> getBadgesByEmployeeId(Long employeeId) {
        if(!repositoryUser.existsById(employeeId)){
            throw new UserNotFoundException(employeeId);
        }
        return repositoryBadge.findBadgesByEmployeeId(employeeId);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Badge> findByName(String name) {
        if(!repositoryBadge.findBadgeByName(name).isPresent()){
            throw new BadgeNotFoundException("No existe badge con nombre: " + name);
        }
        return repositoryBadge.findBadgeByName(name);
    }
    
}