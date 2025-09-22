package com.desarrollox.learncompany.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.desarrollox.learncompany.persistence.entity.UserEntity;

@Repository
public interface JpaRepositoryUser extends JpaRepository<UserEntity, Long>{
    
}