package com.enigmacamp.restapiintro.services.interfaces;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.shared.classes.PagedResponse;
import com.enigmacamp.restapiintro.shared.utils.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course create(CreateCourseRequestDto createCourseRequestDto);

    Iterable<Course> getAll(Pageable pageable);
    Iterable<Course> getAll(Course filter, Boolean shouldMatchAll, Pageable pageable) throws Exception;
    Page<Course> getAll(List<SearchCriteria> searchCriteria, Pageable pageable) throws Exception;

    Optional<Course> getById(String id);

    Course update(String id, Course course);

    String remove(String id);


    PagedResponse<Course> getAllPaged(Pageable pageable);
}
