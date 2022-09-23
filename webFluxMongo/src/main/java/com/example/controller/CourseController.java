package com.example.controller;


import com.example.entity.Course;
import com.example.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping(path = "/courses")
    public Mono<Course> createCourse(@RequestBody Course course) {

        return courseService.createCourse(course);

    }
    @GetMapping(path = "/courses")
    public Flux<Course> showAllClosedCourses(@RequestParam(name = "show-closed",required = false) Boolean isClosed){

        return courseService.findAllClosedCourses(isClosed);

    }

    @GetMapping(path = "/courses/{id}")
    public Mono<ResponseEntity<Course>> getCourseById(@PathVariable(name = "id") String id){

        return courseService.findById(id);

    }

    @PutMapping(path = "/courses/{id}")
    public Mono<ResponseEntity<Course>> updateCourse(@PathVariable(name = "id") String id,@RequestBody Course course) {

        return courseService.updateCourse(id,course);

    }

    @DeleteMapping(path = "/courses/{id}")
    public Mono<ResponseEntity<Void>> deleteCourseById(@PathVariable(name = "id") String id) {

        return courseService.deleteCourseById(id);


    }

    @PutMapping(path = "/courses/{id}/students")
    public Mono<ResponseEntity<Void>>  addStudentsToTheCourse(@PathVariable(name = "id") String id,
                                             @RequestParam(name = "names",required = false) List<String> names) {

        return courseService.addStudents(id,names);

    }

    @GetMapping(path = "/courses/{id}/students")
    public Flux<String> getAllStudentsOfTheCourse(@PathVariable(name = "id") String id) {

        return courseService.getAllStudentsOfTheCourse(id);

    }

    @DeleteMapping(path = "/courses/{id}/students")
    public Mono<ResponseEntity<Void>>  removeStudentFromTheCourseByName(@RequestParam(name = "names",required = false) List<String> names,
                                                       @PathVariable(name = "id") String id) {
        return courseService.removeStudentsFromTheCourse(names,id);

    }

    @PutMapping(path = "/courses/{id}/toggle-course")
    public Mono<ResponseEntity<Void>> toggleCourse(@PathVariable(name = "id") String id) {

        return courseService.toggleCourse(id);

    }




}


