package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;
import com.desarrollox.learncompany.domain.model.Module;

public interface IModuleService {
    Module createModule(Module module);
    Optional<Module> getModuleById(Long id);
    List<Module> getAllModules();
    Optional<Module> deleteModule(Long id);
    List<Module> getModulesByCourseId(Long courseId);
}