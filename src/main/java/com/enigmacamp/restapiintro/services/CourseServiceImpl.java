package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.repositories.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    private CourseRepository courseRepository;
    private ModelMapper modelMapper;

    public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public Course create(CreateCourseRequestDto createCourseRequestDto) {
        Course course = modelMapper.map(createCourseRequestDto, Course.class);
        return courseRepository.insert(course);
    }

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getById(String id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course update(String id, Course course) {
        return courseRepository.update(id, course);
    }

    @Override
    public String remove(String id) {
        return courseRepository.delete(id);
    }

    @Override
    public Set<Course> getAll(Course filter, Boolean shouldMatchAll) {
        Set<Course> courseSet = new HashSet<>();

        if (filter.getTitle() != null) {
            courseSet.addAll(courseRepository.findAll("title", filter.getTitle()));
        }
        if (filter.getDescription() != null) {
            if (shouldMatchAll) {
                courseSet = courseSet.stream()
                        .filter(c -> c.getDescription().toLowerCase().contains(filter.getDescription().toLowerCase()))
                        .collect(Collectors.toSet());
            } else {
                courseSet.addAll(courseRepository.findAll("description", filter.getDescription()));
            }
        }
        if (filter.getSlug() != null) {
            if (shouldMatchAll) {
                courseSet = courseSet.stream()
                        .filter(c -> c.getSlug().toLowerCase().contains(filter.getSlug().toLowerCase()))
                        .collect(Collectors.toSet());
            } else {
                courseSet.addAll(courseRepository.findAll("slug", filter.getSlug()));
            }
        }
        return courseSet;
    }
}
