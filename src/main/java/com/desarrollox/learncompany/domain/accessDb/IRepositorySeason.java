package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Season;

public interface IRepositorySeason {
    Season save(Season season);
    Optional<Season> findById(Long id);
    List<Season> findAll();
}