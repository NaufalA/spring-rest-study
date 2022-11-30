package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course create(CreateCourseRequestDto createCourseRequestDto);
    List<Course> getAll();
    Optional<Course> getById(String id);
    Course update(String id, Course course);
    String remove(String id);
}
