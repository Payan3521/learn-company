package com.desarrollox.learncompany.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.SeasonEntity;

@Repository
public interface JpaRepositorySeason extends JpaRepository<SeasonEntity, Long> {
    
}