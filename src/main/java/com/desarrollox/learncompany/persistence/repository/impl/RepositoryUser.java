package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryUser;
import com.desarrollox.learncompany.domain.model.Employee;
import com.desarrollox.learncompany.domain.model.User;
import com.desarrollox.learncompany.domain.model.User.Role;
import com.desarrollox.learncompany.persistence.entity.UserEntity;
import com.desarrollox.learncompany.persistence.mapper.EmployeeMapper;
import com.desarrollox.learncompany.persistence.mapper.PolymorphicUserMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryUser;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryUser implements IRepositoryUser {

    private final JpaRepositoryUser jpaRepositoryUser;
    private final PolymorphicUserMapper polymorphicUserMapper;
    private final EmployeeMapper employeeMapper;

    @Override
    public User save(User user) {
        return polymorphicUserMapper.toDomain(jpaRepositoryUser.save(polymorphicUserMapper.toEntity(user)));
    }

    @Override
    public Optional<User> findById(Long id) {
       return jpaRepositoryUser.findById(id).map(polymorphicUserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepositoryUser.findByEmail(email).map(polymorphicUserMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepositoryUser.findAll().stream().map(polymorphicUserMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<User> update(Long id, User user) {
        return jpaRepositoryUser.findById(id).map(userEntity -> {
            user.setId(id);
            UserEntity updatedUser = jpaRepositoryUser.save(polymorphicUserMapper.toEntity(user));
            return polymorphicUserMapper.toDomain(updatedUser);
        });
    }

    @Override
    public Optional<User> delete(Long id) {
        return jpaRepositoryUser.findById(id).map(userEntity -> {
            userEntity.setStatus(false);
            UserEntity userDeleted = jpaRepositoryUser.save(userEntity);
            return polymorphicUserMapper.toDomain(userDeleted);
        });
    }

    @Override
    public List<Employee> getRankingByDepartment(Long departmentId) {
        return jpaRepositoryUser.findByDepartmentOrderByPuntosDesc(departmentId).stream()
                .map(employeeMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findUsersByFilters(Long departmentId, Role role, boolean status) {
        return jpaRepositoryUser.findByFilters(departmentId, role, status).stream()
                .map(polymorphicUserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepositoryUser.existsById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepositoryUser.existsByEmail(email);
    }
    
}