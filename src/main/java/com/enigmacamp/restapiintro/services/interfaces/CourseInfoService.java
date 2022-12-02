package com.enigmacamp.restapiintro.services.interfaces;

import com.enigmacamp.restapiintro.models.CourseInfo;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseInfoRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CourseInfoService {
    CourseInfo create(CreateCourseInfoRequestDto createCourseRequestDto);

    Page<CourseInfo> getAll(Pageable pageable);

    Page<CourseInfo> getAll(CourseInfo filter, Boolean shouldMatchAll, Pageable pageable) throws Exception;

    Optional<CourseInfo> getById(String id);

    CourseInfo update(String id, CourseInfo courseInfo);

    String remove(String id);
}
