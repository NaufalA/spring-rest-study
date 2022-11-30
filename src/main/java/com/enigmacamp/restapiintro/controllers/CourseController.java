package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.services.CourseService;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Course> create(@RequestBody CreateCourseRequestDto createCourseRequestDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.create(createCourseRequestDto));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getAll());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getById(@PathVariable("id") String id) {
        try {
            Optional<Course> course = courseService.getById(id);
            if (course.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(course.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable("id") String id, @RequestBody Course course) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.update(id, course));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(courseService.remove(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
