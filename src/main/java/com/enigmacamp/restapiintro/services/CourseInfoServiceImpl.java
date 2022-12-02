package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.CourseInfo;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseInfoRequestDto;
import com.enigmacamp.restapiintro.repositories.interfaces.CourseInfoRepository;
import com.enigmacamp.restapiintro.services.interfaces.CourseInfoService;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseInfoServiceImpl implements CourseInfoService {
    @Autowired
    private CourseInfoRepository courseInfoRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CourseInfo create(CreateCourseInfoRequestDto createCourseRequestDto) {
        return courseInfoRepository.save(modelMapper.map(createCourseRequestDto, CourseInfo.class));
    }

    @Override
    public Page<CourseInfo> getAll(Pageable pageable) {
        return courseInfoRepository.findAll(pageable);
    }

    @Override
    public Page<CourseInfo> getAll(CourseInfo filter, Boolean shouldMatchAll, Pageable pageable) throws Exception {
        return courseInfoRepository.findAll(pageable);
    }

    @Override
    public Optional<CourseInfo> getById(String id) {
        return courseInfoRepository.findById(id);
    }

    @Override
    public CourseInfo update(String id, CourseInfo course) {
        Optional<CourseInfo> existingCourse = getById(id);
        if (existingCourse.isPresent()) {
            course.setId(existingCourse.get().getId());
            return courseInfoRepository.save(course);
        } else {
            throw new NotFoundException("Course Data Not Found");
        }
    }

    @Override
    public String remove(String id) {
        Optional<CourseInfo> existingCourse = getById(id);
        if (existingCourse.isPresent()) {
            courseInfoRepository.deleteById(id);
            return id;
        } else {
            throw new NotFoundException("Course Data Not Found");
        }
    }
}
