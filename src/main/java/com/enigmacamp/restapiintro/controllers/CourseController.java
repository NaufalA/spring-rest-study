package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.services.CourseService;
import com.enigmacamp.restapiintro.shared.classes.CommonResponse;
import com.enigmacamp.restapiintro.shared.classes.ErrorResponse;
import com.enigmacamp.restapiintro.shared.classes.SuccessResponse;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@RequestBody CreateCourseRequestDto createCourseRequestDto) {
        try {
            Course createdCourse = courseService.create(createCourseRequestDto);
            SuccessResponse<Course> response = new SuccessResponse<>(
                    HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), createdCourse
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAll(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "link", required = false) String link,
            @RequestParam(value = "filterType", required = false) String filterType
    ) {
        try {
            if (title == null && description == null && link == null) {
                List<Course> courses = courseService.getAll();
                SuccessResponse<List<Course>> response = new SuccessResponse<>(
                        HttpStatus.OK.value(), HttpStatus.OK.toString(), courses
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                Course filterModel = new Course();
                filterModel.setTitle(title);
                filterModel.setDescription(description);
                filterModel.setSlug(link);
                Boolean shouldMatchAll = filterType != null && filterType.equalsIgnoreCase("and");

                Set<Course> courseSet = courseService.getAll(filterModel, shouldMatchAll);
                CommonResponse response = new SuccessResponse<>(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.toString(),
                        courseSet
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable("id") String id) {
        try {
            Optional<Course> course = courseService.getById(id);
            if (course.isPresent()) {
                SuccessResponse<Course> response = new SuccessResponse<>(
                        HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), course.get()
                );
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                ErrorResponse response = new ErrorResponse(
                        HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (NotFoundException e) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable("id") String id, @RequestBody Course course) {
        try {
            Course savedCourse = courseService.update(id, course);
            SuccessResponse<Course> response = new SuccessResponse<>(
                    HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), savedCourse
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> remove(@PathVariable("id") String id) {
        try {
            String deletedId = courseService.remove(id);
            SuccessResponse<String> response = new SuccessResponse<>(
                    HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), deletedId
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (NotFoundException e) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            ErrorResponse response = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
