package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.repositories.specifications.CourseSpecification;
import com.enigmacamp.restapiintro.models.CourseType;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.repositories.interfaces.CourseRepository;
import com.enigmacamp.restapiintro.services.interfaces.CourseService;
import com.enigmacamp.restapiintro.services.interfaces.CourseTypeService;
import com.enigmacamp.restapiintro.shared.classes.PagedResponse;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import com.enigmacamp.restapiintro.shared.utils.SearchCriteria;
import com.enigmacamp.restapiintro.shared.utils.SearchOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
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
    public Page<Course> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Page<Course> getAll(List<SearchCriteria> searchCriteria, Pageable pageable) {
        Specification<Course> courseSpecification = Specification.where(new CourseSpecification(searchCriteria.get(0)));

        for (int i = 1; i < searchCriteria.size(); i++) {
            SearchCriteria searchCriterion = searchCriteria.get(i);
            CourseSpecification newSpecs = new CourseSpecification(
                    searchCriterion
            );
            if (searchCriterion.getSearchOperation() == SearchOperation.AND) {
                courseSpecification = Specification.where(courseSpecification).and(newSpecs);
            } else {
                courseSpecification = Specification.where(courseSpecification).or(newSpecs);
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
            course.setCourseType(updatedCourseType.get());
        }

        return courseRepository.save(course);
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
