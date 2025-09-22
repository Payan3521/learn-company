package com.desarrollox.learncompany.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.ModuleEntity;

@Repository
public interface JpaRepositoryModule extends JpaRepository<ModuleEntity, Long> {
    
}