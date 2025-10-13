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
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.EmployeeBadge;
import com.desarrollox.learncompany.domain.service.IEmployeeBadgeService;
import com.desarrollox.learncompany.web.dto.AssignBadgeRequest;
import com.desarrollox.learncompany.web.dto.EmployeeBadgeResponse;
import com.desarrollox.learncompany.web.webMapper.EmployeeBadgeWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/employeeBadge")
@RequiredArgsConstructor
public class EmployeeBadgeController {

    private final IEmployeeBadgeService employeeBadgeService;
    private final EmployeeBadgeWebMapper employeeBadgeWebMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeBadgeResponse>> assignBadgeToEmployee(@Valid @RequestBody AssignBadgeRequest request){
        EmployeeBadge employeeBadge = employeeBadgeWebMapper.requestToDomain(request);
        EmployeeBadge employeeBadgeSaved = employeeBadgeService.assignBadgeToEmployee(employeeBadge);
        EmployeeBadgeResponse response = employeeBadgeWebMapper.domainToResponse(employeeBadgeSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("insignia asignada correctamente", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeBadgeResponse>> delete(@PathVariable Long id){
        EmployeeBadge employeeeBadgeDeleted = employeeBadgeService.delete(id).get();
        EmployeeBadgeResponse employeeBadgeResponse = employeeBadgeWebMapper.domainToResponse(employeeeBadgeDeleted);
        return ResponseEntity.ok(ApiResponse.success("Insignia eliminada a empleado correctamente", employeeBadgeResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeBadgeResponse>> findById(@PathVariable Long id){
        EmployeeBadge employeeBadge = employeeBadgeService.findById(id).get();
        EmployeeBadgeResponse employeeBadgeResponse = employeeBadgeWebMapper.domainToResponse(employeeBadge);
        return ResponseEntity.ok(ApiResponse.success("isignia de empleado obtenido correctamente", employeeBadgeResponse));

    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EmployeeBadgeResponse>>> findAll(){
        List<EmployeeBadge> employeeBadges = employeeBadgeService.findAll();

        if(employeeBadges.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<EmployeeBadgeResponse> employeeBadgeResponses = employeeBadges.stream().map(employeeBadgeWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("insignias encontradas", employeeBadgeResponses));
    }
}