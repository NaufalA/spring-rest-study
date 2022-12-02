package com.enigmacamp.restapiintro.services.interfaces;

import com.enigmacamp.restapiintro.models.CourseType;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseTypeRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CourseTypeService {
    CourseType create(CreateCourseTypeRequestDto createCourseRequestDto);

    Page<CourseType> getAll(Pageable pageable);

    Page<CourseType> getAll(CourseType filter, Boolean shouldMatchAll, Pageable pageable) throws Exception;

    Optional<CourseType> getById(String id);

    CourseType update(String id, CourseType courseType);

    String remove(String id);
}
