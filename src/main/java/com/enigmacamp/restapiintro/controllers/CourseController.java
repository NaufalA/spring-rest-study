package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.services.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public Course create(@RequestBody Course course) {
        try {
            return courseService.create(course);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public List<Course> getAll() {
        try {
            return courseService.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public Course getById(@PathVariable("id") String id) {
        try {
            Optional<Course> course = courseService.getById(id);
            return course.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public Course update(@PathVariable("id") String id, @RequestBody Course course) {
        try {
            return courseService.update(id, course);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable("id") String id) {
        try {
            return courseService.remove(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
