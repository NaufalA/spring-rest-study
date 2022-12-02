package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.CourseType;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseTypeRequestDto;
import com.enigmacamp.restapiintro.repositories.interfaces.CourseTypeRepository;
import com.enigmacamp.restapiintro.services.interfaces.CourseTypeService;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseTypeServiceImpl implements CourseTypeService {
    @Autowired
    private CourseTypeRepository courseTypeRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public CourseType create(CreateCourseTypeRequestDto createCourseRequestDto) {
        return courseTypeRepository.save(modelMapper.map(createCourseRequestDto, CourseType.class));
    }

    @Override
    public Page<CourseType> getAll(Pageable pageable) {
        return courseTypeRepository.findAll(pageable);
    }

    @Override
    public Page<CourseType> getAll(CourseType filter, Boolean shouldMatchAll, Pageable pageable) {
        return courseTypeRepository.findAll(pageable);
    }

    @Override
    public Optional<CourseType> getById(String id) {
        return courseTypeRepository.findById(id);
    }

    @Override
    public CourseType update(String id, CourseType course) {
        Optional<CourseType> existingCourse = getById(id);
        if (existingCourse.isPresent()) {
            course.setId(existingCourse.get().getId());
            return courseTypeRepository.save(course);
        } else {
            throw new NotFoundException("Course Data Not Found");
        }
    }

    @Override
    public String remove(String id) {
        Optional<CourseType> existingCourse = getById(id);
        if (existingCourse.isPresent()) {
            courseTypeRepository.deleteById(id);
            return id;
        } else {
            throw new NotFoundException("Course Data Not Found");
        }
    }
}
