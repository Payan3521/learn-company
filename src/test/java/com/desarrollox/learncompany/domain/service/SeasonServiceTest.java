package com.desarrollox.learncompany.domain.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.desarrollox.learncompany.core.logging.LoggingService;
import com.desarrollox.learncompany.domain.accessDb.IRepositorySeason;
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

    }

    @Test //409
    void creatSeason_SeasonAlreadyRegistered(){

    }

    @Test //200
    void getSeasonById_success(){

    }

    @Test //404
    void getSeasonById_SeasonNotFound(){

    }

    @Test //200
    void getAllSeasons_success(){

    }

    @Test //204
    void getAllSeasons_isEmpty(){

    }
    
}