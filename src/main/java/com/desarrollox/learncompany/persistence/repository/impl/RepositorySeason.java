package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositorySeason;
import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.persistence.mapper.SeasonMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositorySeason;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositorySeason implements IRepositorySeason{

    private final JpaRepositorySeason jpaRepositorySeason;
    private final SeasonMapper seasonMapper;
    
    @Override
    public Season save(Season season) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public Optional<Season> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Season> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public boolean existsById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }
    
}