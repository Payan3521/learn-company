package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositorySeason;
import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.domain.service.ISeasonService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeasonService implements ISeasonService {

    private final IRepositorySeason repositorySeason;

    @Override
    public Season creatSeason(Season season) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'creatSeason'");
    }

    @Override
    public Optional<Season> getSeasonById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getSeasonById'");
    }

    @Override
    public List<Season> getAllSeasons() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllSeasons'");
    }
    
}
