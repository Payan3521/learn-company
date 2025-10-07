package com.desarrollox.learncompany.persistence.repository.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.desarrollox.learncompany.persistence.entity.ModuleEntity;
import com.desarrollox.learncompany.persistence.mapper.ModuleMapper;
import com.desarrollox.learncompany.persistence.repository.JpaRepositoryModule;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryModule;
import com.desarrollox.learncompany.domain.model.Module;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RepositoryModule implements IRepositoryModule{
    
    private final JpaRepositoryModule jpaRepositoryModule;
    private final ModuleMapper moduleMapper;

    @Override
    public Module save(Module module) {
        return moduleMapper.toDomain(
                jpaRepositoryModule.save(
                    moduleMapper.toEntity(module)
                )
        );
    }

    @Override
    public Optional<Module> findById(Long id) {
        return jpaRepositoryModule.findById(id)
                .map(moduleMapper::toDomain);
    }

    @Override
    public List<Module> findAll() {
        return jpaRepositoryModule.findAll()
                .stream().map(moduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Module> delete(Long id) {
        return jpaRepositoryModule.findById(id).map(moduleEntity -> {
            ModuleEntity moduleDeleted = jpaRepositoryModule.save(moduleEntity);
            return moduleMapper.toDomain(moduleDeleted);
        });
    }

    
    @Override
    public List<Module> findModulesByCourseId(Long courseId) {
        return jpaRepositoryModule.findByCourseId(courseId)
                .stream()
                .map(moduleMapper::toDomain)
                .collect(Collectors.toList());
    }
}