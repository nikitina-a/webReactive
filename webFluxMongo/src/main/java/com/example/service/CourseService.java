package com.example.service;

import com.example.entity.Course;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CourseService {


    Mono<Course> createCourse(Course course);

    Flux<Course> findAllClosedCourses(Boolean isClosed);

    Mono<ResponseEntity<Course>> findById(String id);

    Mono<ResponseEntity<Course>> updateCourse(String id,Course course);

    Mono<ResponseEntity<Void>> deleteCourseById(String id);

    Mono<ResponseEntity<Void>>  addStudents(String id, List<String> names);

    Flux<String> getAllStudentsOfTheCourse(String id);

    Mono<ResponseEntity<Void>> removeStudentsFromTheCourse(List<String> names, String id);

    Mono<ResponseEntity<Void>> toggleCourse(String id);
}
