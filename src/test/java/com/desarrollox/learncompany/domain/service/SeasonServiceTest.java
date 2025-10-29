package com.desarrollox.learncompany.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositorySeason;
import com.desarrollox.learncompany.domain.exception.SeasonAlreadyCreatedException;
import com.desarrollox.learncompany.domain.exception.SeasonNotFoundException;
import com.desarrollox.learncompany.domain.model.Season;
import com.desarrollox.learncompany.domain.service.impl.SeasonService;

@ExtendWith(MockitoExtension.class)
class SeasonServiceTest {
    @Mock
    private IRepositorySeason repositorySeason;

    @Mock
    private LoggingService loggingService;

    @InjectMocks
    private SeasonService seasonService;

    private Season season;
    private List<Season> seasonList;

    @BeforeEach
    void setUp(){
        season = new Season();
        season.setId(1L);
        season.setDuration(600);

        seasonList = new ArrayList<>();
        seasonList.add(season);
    }

    @Test //201
    void creatSeason_success(){
        when(repositorySeason.findAll()).thenReturn(new ArrayList<>());
        when(repositorySeason.save(any(Season.class))).thenReturn(season);

        Season result = seasonService.creatSeason(season);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(repositorySeason).findAll();
        verify(repositorySeason).save(any(Season.class));
    }

    @Test //409
    void creatSeason_SeasonAlreadyRegistered(){
        when(repositorySeason.findAll()).thenReturn(seasonList);

        SeasonAlreadyCreatedException thrown = assertThrows(
            SeasonAlreadyCreatedException.class,
            () -> seasonService.creatSeason(season)
        );

        assertNotNull(thrown);
        verify(repositorySeason).findAll();
        verify(repositorySeason, never()).save(any(Season.class));
    }

    @Test //200
    void getSeasonById_success(){
        when(repositorySeason.existsById(anyLong())).thenReturn(true);
        when(repositorySeason.findById(anyLong())).thenReturn(Optional.of(season));

        Optional<Season> result = seasonService.getSeasonById(1L);

        assertTrue(result.isPresent());
        assertEquals(season, result.get());
        assertEquals(season.getId(), result.get().getId());

        verify(repositorySeason).existsById(anyLong());
        verify(repositorySeason).findById(anyLong());
    }

    @Test //404
    void getSeasonById_SeasonNotFound(){
        when(repositorySeason.existsById(anyLong())).thenReturn(false);

        SeasonNotFoundException thrown = assertThrows(
            SeasonNotFoundException.class, 
            () -> seasonService.getSeasonById(1L)
            );

        assertNotNull(thrown);
        verify(repositorySeason).existsById(anyLong());
        verify(repositorySeason, never()).findById(anyLong());
    }

    @Test //200
    void getAllSeasons_success(){
        when(repositorySeason.findAll()).thenReturn(seasonList);
        
        List<Season> result = seasonService.getAllSeasons();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(seasonList, result);

        verify(repositorySeason).findAll();
    }


    @Test //204
    void getAllSeasons_isEmpty(){
        when(repositorySeason.findAll()).thenReturn(new ArrayList<>());

        List<Season> result = seasonService.getAllSeasons();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repositorySeason).findAll();
    }
    
}