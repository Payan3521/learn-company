package com.desarrollox.learncompany.domain.accessDb;

import java.util.List;
import com.desarrollox.learncompany.domain.model.Module;
import java.util.Optional;

public interface IRepositoryModule {
    Module save(Module module);
    Optional<Module> findById(Long id);
    List<Module> findAll();
    Optional<Module> delete(Long id);
    List<Module> findModulesByCourseId(Long courseId);
}