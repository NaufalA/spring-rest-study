package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.repositories.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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
    public Set<Course> getAll(Course filter, Boolean shouldMatchAll) throws Exception {
        Set<Course> courseSet = new HashSet<>();
        Class<? extends Course> courseClass = filter.getClass();
        Field[] fields = courseClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(filter) != null) {
                courseSet.addAll(courseRepository.findAll(field.getName(), (String) field.get(filter)));
                if (shouldMatchAll) {
                    courseSet = courseSet.stream()
                            .filter(c -> {
                                try {
                                    Field lookupField = c.getClass().getDeclaredField(field.getName());
                                    lookupField.setAccessible(true);
                                    return lookupField.get(c).toString().toLowerCase()
                                            .contains(field.get(filter).toString().toLowerCase());
                                } catch (IllegalAccessException | NoSuchFieldException e) {
                                    throw new RuntimeException(e);
                                }
                            }).collect(Collectors.toSet());
                }
            }
        }
        return courseSet;
    }
}
