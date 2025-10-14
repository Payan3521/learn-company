package com.desarrollox.learncompany.domain.service;

import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Statistic;

public interface IStatisticService {
    Optional<Statistic> getStatistic();
}
