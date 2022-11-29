package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Override
    public Course create(Course course) {
        return courseRepository.insert(course);
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getById(String id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course update(String id, Course course) {
        return courseRepository.update(id, course);
    }

    @Override
    public String remove(String id) {
        return courseRepository.delete(id);
    }
}