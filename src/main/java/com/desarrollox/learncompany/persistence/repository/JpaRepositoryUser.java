package com.desarrollox.learncompany.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.desarrollox.learncompany.persistence.entity.UserEntity;

public interface JpaRepositoryUser extends JpaRepository<UserEntity, Long>{
    
}