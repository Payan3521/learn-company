package com.desarrollox.learncompany.domain.accessDb;

import com.desarrollox.learncompany.domain.model.User;

public interface IRepositoryUser {
    User save (User user);
}