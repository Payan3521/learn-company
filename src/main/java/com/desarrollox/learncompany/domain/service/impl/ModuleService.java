package com.desarrollox.learncompany.domain.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.domain.accessDb.IRepositoryModule;
import com.desarrollox.learncompany.domain.service.IModuleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleService implements IModuleService {

    private final IRepositoryModule repositoryModule;

    @Override
    public Module createModule(Module module) {
        return repositoryModule.save(module);
    }

    @Override
    public Optional<Module> getModuleById(Long id) {
        return repositoryModule.findById(id);
    }

    @Override
    public List<Module> getAllModules() {
        return repositoryModule.findAll();
    }

    @Override
    public Optional<Module> deleteModule(Long id) {
        return repositoryModule.delete(id);
    }

    @Override
    public List<Module> getModulesByCourseId(Long courseId) {
        return repositoryModule.findModulesByCourseId(courseId);
    }

    
    
}
