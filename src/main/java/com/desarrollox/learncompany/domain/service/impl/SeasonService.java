package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.domain.accessDb.IRepositorySeason;
import com.desarrollox.learncompany.domain.exception.SeasonAlreadyCreatedException;
import com.desarrollox.learncompany.domain.exception.SeasonNotFoundException;
import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.domain.service.ISeasonService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeasonService implements ISeasonService {

    private final IRepositorySeason repositorySeason;

    @Transactional(readOnly = false)
    @Override
    public Season creatSeason(Season season) {
        if(!repositorySeason.findAll().isEmpty()){
            throw new SeasonAlreadyCreatedException();
        }
        return repositorySeason.save(season);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Season> getSeasonById(Long id) {
        if(!repositorySeason.existsById(id)){
            throw new SeasonNotFoundException(id);
        }
        return repositorySeason.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Season> getAllSeasons() {
        return repositorySeason.findAll();
    }
    
}