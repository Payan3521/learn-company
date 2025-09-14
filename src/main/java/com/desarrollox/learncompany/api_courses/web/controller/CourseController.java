package com.desarrollox.learncompany.api_courses.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.api_courses.web.dto.CourseRequest;
import com.desarrollox.learncompany.api_courses.web.dto.CourseResponse;
import com.desarrollox.learncompany.api_modules.web.dto.ModuleResponse;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    @PostMapping("/create-course")
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(
        @RequestBody CourseRequest instructorRequest){

            return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Curso registrado correctamente", null));
    }

    @GetMapping("/getAllCourses")
    public ResponseEntity<ApiResponse<CourseResponse>> getAllCourse(){
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Cursos encontrados", null));
    }

    @GetMapping("/getCourseById/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getByIdCourse(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Curso encontrado", null));
    }

    @GetMapping
    public ResponseEntity <ApiResponse<CourseResponse>> getByFilters(@RequestParam String departament,@RequestParam String name){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Cursos encontrado por filtros", null));
    }

    @GetMapping("/registered-getById/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getByIdregistered(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Curso encontrado", null));
    }    

    @GetMapping("/finalized-getById/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getByIdFinalized(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Curso con finaliadores encontrado", null));
    }    

    @GetMapping("/optional")
    public ResponseEntity <ApiResponse<CourseResponse>> getByOptional(@RequestParam String departament){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Cursos encontrado por filtros", null));
    }

    @GetMapping("/mandatory")
    public ResponseEntity <ApiResponse<CourseResponse>> getByMandatory(@RequestParam String departament){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Cursos encontrado por filtros", null));
    }

    @GetMapping("/modules-getById/{id}")
    public ResponseEntity<ApiResponse<ModuleResponse>> getByIdModules(@PathVariable Long id){

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Modulo encontrado", null));
    }    

    @DeleteMapping("/delete-course/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> deleteCourse(@PathVariable Long id){
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success("Curso eliminado", null));
    }
}
