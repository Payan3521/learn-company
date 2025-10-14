package com.desarrollox.learncompany.domain.service.impl;

import java.util.Optional;
import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryCourse;
import com.desarrollox.learncompany.domain.model.Statistic;
import com.desarrollox.learncompany.domain.service.IStatisticService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatisticService implements IStatisticService {

    private final IRepositoryCourse repositoryCourse;

    @Override
    public Optional<Statistic> getStatistic() {

        

        return ;
    }
    
}
