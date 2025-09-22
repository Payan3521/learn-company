package com.desarrollox.learncompany.persistence.repository.impl;

import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryUser;
import com.desarrollox.learncompany.persistence.repository.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryUser implements IRepositoryUser{

    private final JpaRepositoryUser jpaRepositoryUser;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        return userMapper.toUser(jpaRepositoryUser.save(userMapper.toUserEntity(user)));
    }
    
}