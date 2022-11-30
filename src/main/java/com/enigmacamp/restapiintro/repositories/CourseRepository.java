package com.enigmacamp.restapiintro.repositories;

import com.enigmacamp.restapiintro.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    Course insert(Course course);

    List<Course> findAll();

    Optional<Course> findById(String id);

    Course update(String id, Course course);

    String delete(String id);

    List<Course> findAll(String field, String value);
}
