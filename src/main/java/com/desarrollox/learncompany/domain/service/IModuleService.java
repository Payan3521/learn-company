package com.desarrollox.learncompany.domain.service;

import java.util.List;
import java.util.Optional;

public interface IModuleService {
    Module createModule(Module module);
    Optional<Module> getModuleById(Long id);
    List<Module> getAllModules();
    Optional<Module> deleteModule(Long id);
    List<Module> getModulesByCourseId(Long courseId);
}