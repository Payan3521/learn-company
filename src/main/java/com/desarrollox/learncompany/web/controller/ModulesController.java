package com.desarrollox.learncompany.web.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.domain.model.Module;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.service.IModuleService;
import com.desarrollox.learncompany.web.dto.ModuleRequest;
import com.desarrollox.learncompany.web.dto.ModuleResponse;
import com.desarrollox.learncompany.web.webMapper.ModuleWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModulesController {

    private final IModuleService moduleService;
    private final ModuleWebMapper moduleWebMapper; 
    
    @PostMapping
    public ResponseEntity<ApiResponse<ModuleResponse>> createModule(@Valid @RequestBody ModuleRequest request){
        Module module = moduleWebMapper.requestToDomain(request);
        Module createdModule = moduleService.createModule(module);
        ModuleResponse response = moduleWebMapper.domainToResponse(createdModule);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Modulo creado correctamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ModuleResponse>> getModuleById(@PathVariable Long id){
        Module module = moduleService.getModuleById(id).get();
        ModuleResponse response = moduleWebMapper.domainToResponse(module);
        return ResponseEntity.ok(ApiResponse.success("Modulo encontrado correctamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ModuleResponse>>> getAllModules(){
        List<Module> modules = moduleService.getAllModules();
        List<ModuleResponse> response = modules.stream().map(moduleWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Modulos obtenidos correctamente", response));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<ApiResponse<List<ModuleResponse>>> getModulesByCourseId(@PathVariable Long id){
        List<Module> modules = moduleService.getModulesByCourseId(id);
        List<ModuleResponse> response = modules.stream().map(moduleWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Modulos obtenidos correctamente", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ModuleResponse>> deleteModule(@PathVariable Long id){
        Module moduleDeleted = moduleService.deleteModule(id).get();
        ModuleResponse moduleResponse = moduleWebMapper.domainToResponse(moduleDeleted);
        return ResponseEntity.ok(ApiResponse.success("Modulo eliminado correctamente", moduleResponse));
    }

}