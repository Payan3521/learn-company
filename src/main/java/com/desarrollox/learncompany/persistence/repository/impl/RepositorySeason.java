package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositorySeason;
import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.persistence.mapper.SeasonMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositorySeason;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositorySeason implements IRepositorySeason {

    private final JpaRepositorySeason jpaRepositorySeason;
    private final SeasonMapper seasonMapper;
    private final LoggingService loggingService; // Inyectar LoggingService

    @Override
    public Season save(Season season) {
        loggingService.logInfo("Iniciando guardado de Season con nombre: {}", 
                truncateName(season != null && season.getName() != null ? season.getName() : "null"));
        try {
            Season savedSeason = seasonMapper.toDomain(
                    jpaRepositorySeason.save(
                            seasonMapper.toEntity(season)
                    )
            );
            loggingService.logInfo("Season guardada exitosamente con ID: {} y nombre: {}", 
                    savedSeason.getId(), 
                    truncateName(savedSeason.getName()));
            return savedSeason;
        } catch (Exception e) {
            loggingService.logError("Error al guardar Season con nombre {}: {}", 
                    truncateName(season != null && season.getName() != null ? season.getName() : "null"), 
                    e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<Season> findById(Long id) {
        loggingService.logInfo("Obteniendo Season con ID: {}", id);
        try {
            Optional<Season> season = jpaRepositorySeason.findById(id)
                    .map(seasonMapper::toDomain);
            if (season.isPresent()) {
                loggingService.logInfo("Season ID {} obtenida exitosamente", id);
            } else {
                loggingService.logWarning("Season con ID {} no encontrada", id);
            }
            return season;
        } catch (Exception e) {
            loggingService.logError("Error al obtener Season ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<Season> findAll() {
        loggingService.logInfo("Obteniendo todas las Seasons");
        try {
            List<Season> seasons = jpaRepositorySeason.findAll()
                    .stream()
                    .map(seasonMapper::toDomain)
                    .collect(Collectors.toList());
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

    @Override
    public boolean existsById(Long id) {
        loggingService.logInfo("Verificando existencia de Season con ID: {}", id);
        try {
            boolean exists = jpaRepositorySeason.existsById(id);
            loggingService.logDebug("Season con ID {} existe: {}", id, exists);
            return exists;
        } catch (Exception e) {
            loggingService.logError("Error al verificar existencia de Season ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    // Método auxiliar para truncar nombres largos en los logs
    private String truncateName(String name) {
        if (name == null) {
            return "null";
        }
        return name.length() > 30 ? name.substring(0, 30) + "..." : name;
    }
}