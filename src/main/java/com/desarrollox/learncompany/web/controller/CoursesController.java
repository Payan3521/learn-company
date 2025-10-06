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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.desarrollox.learncompany.core.web.dto.ApiResponse;
import com.desarrollox.learncompany.domain.model.Course;
import com.desarrollox.learncompany.domain.service.ICourseService;
import com.desarrollox.learncompany.web.dto.CourseRequest;
import com.desarrollox.learncompany.web.dto.CourseResponse;
import com.desarrollox.learncompany.web.webMapper.CourseWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CoursesController {

    private final ICourseService courseService;
    private final CourseWebMapper courseWebMapper;
    
    @PostMapping
    public ResponseEntity<ApiResponse<CourseResponse>> createCourse(@Valid @RequestBody CourseRequest request){
        Course course = courseWebMapper.requestToDomain(request);
        Course courseSaved = courseService.createCourse(course);
        CourseResponse response = courseWebMapper.domainToResponse(courseSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Curso creado correctamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getAllCourses(){
        List<Course> courses = courseService.getAllCourses();

        if(courses.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<CourseResponse> courseResponses = courses.stream().map(courseWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Cursos encontrados", courseResponses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> getCourseById(@PathVariable Long id){
        Course course = courseService.getCourseById(id).get();
        CourseResponse courseResponse = courseWebMapper.domainToResponse(course);
        return ResponseEntity.ok(ApiResponse.success("Curso obtenido correctamente", courseResponse));
    }

    @GetMapping("/season/{seasonId}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesBySeasonId(@PathVariable Long seasonId){
        List<Course> courses = courseService.getCoursesBySeasonId(seasonId);


        if(courses.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<CourseResponse> courseResponses = courses.stream().map(courseWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Obtenidos los cursos pertenecientes a la temporada:" + seasonId, courseResponses ));
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesByDepartmentId(@PathVariable Long departmentId){
        List<Course> courses = courseService.findByDepartmentId(departmentId);

        if(courses.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<CourseResponse> courseResponses = courses.stream().map(courseWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Cursos optenidos por temporada: " + departmentId, courseResponses));
    }

    @GetMapping("/by-title")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getCoursesByTitle(@RequestParam(required = true) String title){
        List<Course> courses = courseService.findByTitleContaining(title);

        if(courses.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<CourseResponse> courseResponses = courses.stream().map(courseWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Cursos optenidos", courseResponses));
    }


    @GetMapping("/optional")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getOptionalsByDepartament(@RequestParam(required = true) Long departmentId){
        List<Course> courses = courseService.findByStatusOptional(departmentId);

        if(courses.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<CourseResponse> courseResponses = courses.stream().map(courseWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Cursos opcionales optenidos por temporada: " + departmentId, courseResponses));
    }

    @GetMapping("/mandatory")
    public ResponseEntity<ApiResponse<List<CourseResponse>>> getMandatorysByDepartamet(@RequestParam(required = true) Long departmentId){
        List<Course> courses = courseService.findByStatusMandatory(departmentId);

        if(courses.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        List<CourseResponse> courseResponses = courses.stream().map(courseWebMapper::domainToResponse).collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Cursos obligatorios optenidos por temporada: " + departmentId, courseResponses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CourseResponse>> deleteCourse(@PathVariable Long id){
        Course course = courseService.deleteCourse(id).get();
        CourseResponse courseResponse = courseWebMapper.domainToResponse(course);
        return ResponseEntity.ok(ApiResponse.success("Curso eliminado correctamente", courseResponse));
    }
}