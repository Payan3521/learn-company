package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.desarrollox.learncompany.core.logging.LoggingService;
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
    private final LoggingService loggingService; // Inyectar LoggingService

    @Transactional(readOnly = false)
    @Override
    public Season creatSeason(Season season) {
        loggingService.logInfo("Iniciando creación de Season");
        try {
            if (!repositorySeason.findAll().isEmpty()) {
                loggingService.logError("Ya existe una Season registrada");
                throw new SeasonAlreadyCreatedException();
            }
            Season savedSeason = repositorySeason.save(season);
            loggingService.logInfo("Season creada exitosamente con ID: {}", savedSeason.getId());
            return savedSeason;
        } catch (Exception e) {
            loggingService.logError("Error al crear Season: {}", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Season> getSeasonById(Long id) {
        loggingService.logInfo("Obteniendo Season con ID: {}", id);
        try {
            if (!repositorySeason.existsById(id)) {
                loggingService.logError("Season con ID {} no encontrada", id);
                throw new SeasonNotFoundException(id);
            }
            Optional<Season> season = repositorySeason.findById(id);
            loggingService.logInfo("Season ID {} obtenida exitosamente", id);
            return season;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Season ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Season> getAllSeasons() {
        loggingService.logInfo("Obteniendo todas las Seasons");
        try {
            List<Season> seasons = repositorySeason.findAll();
            if (seasons.isEmpty()) {
                loggingService.logWarning("No se encontraron Seasons");
            } else {
                loggingService.logInfo("Se encontraron {} Seasons", seasons.size());
            }
            return seasons;
        } catch (Exception e) {
            loggingService.logError("Error al obtener todas las Seasons: {}", e.getMessage(), e);
            throw e;
        }
    }
}