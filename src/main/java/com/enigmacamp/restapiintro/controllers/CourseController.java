package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.services.interfaces.CourseService;
import com.enigmacamp.restapiintro.shared.classes.CommonResponse;
import com.enigmacamp.restapiintro.shared.classes.PagedResponse;
import com.enigmacamp.restapiintro.shared.classes.SuccessResponse;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import com.enigmacamp.restapiintro.shared.utils.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@Valid @RequestBody CreateCourseRequestDto createCourseRequestDto) {

        Course createdCourse = courseService.create(createCourseRequestDto);
        SuccessResponse<Course> response = new SuccessResponse<>(
                HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), createdCourse
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "useNative", required = false) boolean useNative,
            @RequestBody(required = false) List<SearchCriteria> criteria
    ) throws Exception {
        Pageable pageable;
        pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sortBy));

        if (useNative) {
            PagedResponse<Course> pagedCourses = courseService.getAllPaged(pageable);

            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.toString(),
                    pagedCourses
            ));
        }

        List<SearchCriteria> searchCriteria = criteria != null ? criteria : new ArrayList<>();
        Page<Course> courses = courseService.getAll(searchCriteria, pageable);
        CommonResponse response;
        response = new SuccessResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                new PagedResponse<>(courses)
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
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
