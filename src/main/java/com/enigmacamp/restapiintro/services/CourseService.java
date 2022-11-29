package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course create(Course course);
    List<Course> getAll();
    Optional<Course> getById(String id);
    Course update(String id, Course course);
    String remove(String id);
}
