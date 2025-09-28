package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Season;

public interface ISeasonService {
    Season creatSeason(Season season);
    Optional<Season> getSeasonById(Long id);
    List<Season> getAllSeasons();
}