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
        Course createdCourse = courseService.create(createCourseRequestDto);
        SuccessResponse<Course> response = new SuccessResponse<>(
                HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), createdCourse
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAll(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam(value = "link", required = false) String link,
            @RequestParam(value = "filterType", required = false) String filterType
    ) throws Exception {
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
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable("id") String id) {
        Optional<Course> course = courseService.getById(id);
        if (course.isPresent()) {
            SuccessResponse<Course> response = new SuccessResponse<>(
                    HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), course.get()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            throw new NotFoundException("No Course with ID " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable("id") String id, @RequestBody Course course) {
        Course savedCourse = courseService.update(id, course);
        SuccessResponse<Course> response = new SuccessResponse<>(
                HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), savedCourse
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> remove(@PathVariable("id") String id) {
        String deletedId = courseService.remove(id);
        SuccessResponse<String> response = new SuccessResponse<>(
                HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), deletedId
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
