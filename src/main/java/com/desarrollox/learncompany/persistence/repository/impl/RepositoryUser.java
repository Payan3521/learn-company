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
        return jpaRepositoryUser.findByIdAndStatusTrue(id)
                .map(polymorphicUserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepositoryUser.findByEmailAndStatusTrue(email)
                .map(polymorphicUserMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepositoryUser.findAllByStatusTrue().stream()
                .map(polymorphicUserMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> update(Long id, User user) {
        return jpaRepositoryUser.findByIdAndStatusTrue(id).map(userEntity -> {
            user.setId(id);
            UserEntity updatedUser = jpaRepositoryUser.save(polymorphicUserMapper.toEntity(user));
            return polymorphicUserMapper.toDomain(updatedUser);
        });
    }

    public Optional<User> delete(Long id) {
        return jpaRepositoryUser.findByIdAndStatusTrue(id).map(userEntity -> {
            userEntity.setStatus(false);
            UserEntity deletedUser = jpaRepositoryUser.save(userEntity);
            return polymorphicUserMapper.toDomain(deletedUser);
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
        return jpaRepositoryUser.existsByIdAndStatusTrue(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepositoryUser.existsByEmailAndStatusTrue(email);
    }

    @Override
    public Optional<User> findByEmailIncludingInactive(String email) {
        return jpaRepositoryUser.findByEmail(email).map(polymorphicUserMapper::toDomain);
    }
    
}