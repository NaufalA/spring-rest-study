package com.enigmacamp.restapiintro.services.interfaces;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.shared.classes.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CourseService {
    Course create(CreateCourseRequestDto createCourseRequestDto);

    Iterable<Course> getAll(Pageable pageable);

    Optional<Course> getById(String id);

    Course update(String id, Course course);

    String remove(String id);

    Iterable<Course> getAll(Course filter, Boolean shouldMatchAll, Pageable pageable) throws Exception;

    PagedResponse<Course> getAllPaged(Pageable pageable);
}
