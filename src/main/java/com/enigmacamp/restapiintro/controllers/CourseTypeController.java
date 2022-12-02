package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.CourseType;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseTypeRequestDto;
import com.enigmacamp.restapiintro.services.interfaces.CourseTypeService;
import com.enigmacamp.restapiintro.shared.classes.CommonResponse;
import com.enigmacamp.restapiintro.shared.classes.PagedResponse;
import com.enigmacamp.restapiintro.shared.classes.SuccessResponse;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/course-types")
public class CourseTypeController {
    private final CourseTypeService courseTypeService;

    public CourseTypeController(CourseTypeService courseTypeService) {
        this.courseTypeService = courseTypeService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> create(@Valid @RequestBody CreateCourseTypeRequestDto createCourseTypeRequestDto) {

        CourseType createdCourseType = courseTypeService.create(createCourseTypeRequestDto);
        SuccessResponse<CourseType> response = new SuccessResponse<>(
                HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), createdCourseType
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "typeName", required = false) String typeName,
            @RequestParam(value = "filterType", required = false) String filterType,
            @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ) throws Exception {
        Pageable pageable;
        pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.valueOf(direction), sortBy));

        CourseType filterModel = new CourseType();
        filterModel.setTypeName(typeName);
        Boolean shouldMatchAll = filterType != null && filterType.equalsIgnoreCase("and");

        Page<CourseType> courseTypes = courseTypeService.getAll(filterModel, shouldMatchAll, pageable);

        CommonResponse response;
        response = new SuccessResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.toString(),
                new PagedResponse<>(courseTypes)
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse> getById(@PathVariable("id") String id) {
        Optional<CourseType> courseType = courseTypeService.getById(id);
        if (courseType.isPresent()) {
            SuccessResponse<CourseType> response = new SuccessResponse<>(
                    HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), courseType.get()
            );
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            throw new NotFoundException("No CourseType with ID " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable("id") String id, @RequestBody CourseType courseType) {
        CourseType savedCourseType = courseTypeService.update(id, courseType);
        SuccessResponse<CourseType> response = new SuccessResponse<>(
                HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), savedCourseType
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> remove(@PathVariable("id") String id) {
        String deletedId = courseTypeService.remove(id);
        SuccessResponse<String> response = new SuccessResponse<>(
                HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), deletedId
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
