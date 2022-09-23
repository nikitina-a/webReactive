package com.example.service.impl;


import com.example.entity.Course;
import com.example.repository.CourseRepository;
import com.example.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {


    private final CourseRepository courseRepository;

    @Override
    public Mono<Course> createCourse(Course course) {


        if (courseRepository.existsByName(course.getName())) {
            throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED);

        }



        return courseRepository.save(course);

    }

    @Override
    public Flux<Course> findAllClosedCourses(Boolean isClosed) {

        if(isClosed == null) {

            return courseRepository.findAll();

        }

        if (isClosed) {

            return courseRepository.findAll().filter(Course::isClosed);
        }

        return courseRepository.findAll().filter(course -> !course.isClosed());

    }

    @Override
    public Mono<ResponseEntity<Course>> findById(String id) {

        return   courseRepository.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Course>> updateCourse(String id,Course course) {

        return courseRepository.findById(id)

                .flatMap(oldCourse -> {
                    oldCourse.setName(course.getName());
                    oldCourse.setClosed(course.isClosed());
                    oldCourse.setStudents(course.getStudents());
                    return courseRepository.save(oldCourse);

                })
                .map(newCourse-> new ResponseEntity<>(newCourse,HttpStatus.OK))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCourseById(String id) {

        return courseRepository.findById(id)
                .flatMap(courseTodDelete ->
                        courseRepository.delete(courseTodDelete)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                                        .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Mono<ResponseEntity<Void>> addStudents(String id, List<String> names) {
        return courseRepository.findById(id)
                .flatMap(course -> {
                    var students = course.getStudents();
                    students.addAll(names);
                    course.setStudents(students);
                    return courseRepository.save(course);

                        })
                .map(course -> new ResponseEntity<Void>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public Flux<String> getAllStudentsOfTheCourse(String id) {

        return courseRepository.findById(id)
                .map(Course::getStudents)
                .flatMapIterable(students->students);
    }

    @Override
    public Mono<ResponseEntity<Void>> removeStudentsFromTheCourse(List<String> names, String id) {

        return courseRepository.findById(id)
                .flatMap(course -> {
                    var set = new HashSet<>(names);
                    var students = course.getStudents();
                    var filtered = students.stream()
                            .filter(set::add)
                            .toList();
                    course.setStudents(filtered);
                    return courseRepository.save(course);


                })
                .map(course -> new ResponseEntity<Void>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));


                }

    @Override
    public Mono<ResponseEntity<Void>> toggleCourse(String id) {
        
        return courseRepository.findById(id)
                .flatMap(course -> {
                    if (!course.isClosed()){
                        course.setClosed(true);
                    } else {
                        course.setClosed(false);
                    }
                    
                    return courseRepository.save(course);
                  
                })
                .map(course -> new ResponseEntity<Void>(HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

