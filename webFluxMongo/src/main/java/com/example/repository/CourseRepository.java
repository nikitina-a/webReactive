package com.example.repository;

import com.example.entity.Course;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Optional;


@Repository
public interface CourseRepository extends ReactiveMongoRepository<Course,String> {

    Optional<Course> findCourseByName(String name);

    boolean existsByName(String name);


    Flux<Course> findAllByClosedIs(Boolean closed);
}
