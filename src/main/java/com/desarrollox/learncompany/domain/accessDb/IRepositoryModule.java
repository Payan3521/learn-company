package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import java.util.Optional;

public interface IRepositoryModule {
    Module save(Module module);
    Optional<Module> findById(Long id);
    List<Module> findAll();
    Optional<Module> delete(Long id);
    List<Module> finModulesByCourseId(Long courseId);
}