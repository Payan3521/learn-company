package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return seasonMapper.toDomain(
                jpaRepositorySeason.save(
                        seasonMapper.toEntity(season)
                )
        );
    }

    @Override
    public Optional<Season> findById(Long id) {
        return jpaRepositorySeason.findById(id)
                .map(seasonMapper::toDomain);
    }

    @Override
    public List<Season> findAll() {
        return jpaRepositorySeason.findAll()
                .stream().map(seasonMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositorySeason.existsById(id);
    }
    
}