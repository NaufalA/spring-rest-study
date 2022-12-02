package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.CourseSpecification;
import com.enigmacamp.restapiintro.models.CourseType;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.repositories.interfaces.CourseRepository;
import com.enigmacamp.restapiintro.services.interfaces.CourseService;
import com.enigmacamp.restapiintro.services.interfaces.CourseTypeService;
import com.enigmacamp.restapiintro.shared.classes.PagedResponse;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.Optional;

public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private CourseTypeService courseTypeService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Course create(CreateCourseRequestDto createCourseRequestDto) {
        Course course = modelMapper.map(createCourseRequestDto, Course.class);
        Optional<CourseType> courseType = courseTypeService.getById(createCourseRequestDto.getCourseType().getId());
        courseType.ifPresent(course::setCourseType);

        return courseRepository.save(course);
    }

    @Override
    public Iterable<Course> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Iterable<Course> getAll(Course filter, Boolean shouldMatchAll, Pageable pageable) throws Exception {
        Field[] fields = filter.getClass().getDeclaredFields();

        Specification<Course> courseSpecification = Specification.where(null);

        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(filter) != null) {
                CourseSpecification newSpecs = new CourseSpecification(
                        field.getName(),
                        field.get(filter).toString(),
                        "contains"
                );
                if (shouldMatchAll) {
                    courseSpecification = Specification.where(courseSpecification).and(newSpecs);
                } else {
                    courseSpecification = Specification.where(courseSpecification).or(newSpecs);
                }
            }
        }

        return courseRepository.findAll(courseSpecification, pageable);
    }

    @Override
    public Optional<Course> getById(String id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course update(String id, Course course) {
        Optional<Course> existingCourse = getById(id);
        if (existingCourse.isEmpty()) {
            throw new NotFoundException("Course Data Not Found");
        }

        Optional<CourseType> updatedCourseType = courseTypeService.getById(course.getCourseType().getId());
        if (updatedCourseType.isEmpty()) {
            throw new NotFoundException("Course Type Data Not Found");
        } else if (!existingCourse.get().getCourseType().equals(updatedCourseType.get())) {
            existingCourse.get().setCourseType(updatedCourseType.get());
        }

        if (!existingCourse.get().getCourseInfo().equals(course.getCourseInfo())) {
            existingCourse.get().setCourseInfo(course.getCourseInfo());
        }

        return courseRepository.save(existingCourse.get());
    }

    @Override
    public String remove(String id) {
        Optional<Course> existingCourse = getById(id);
        if (existingCourse.isPresent()) {
            courseRepository.deleteById(id);
            return id;
        } else {
            throw new NotFoundException("Course Data Not Found");
        }
    }

    @Override
    public PagedResponse<Course> getAllPaged(Pageable pageable) {
        Page<Course> courses = courseRepository.findAllPaged(pageable);

        return new PagedResponse<>(courses);
    }
}
