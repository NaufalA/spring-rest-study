package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.CourseInfo;
import com.enigmacamp.restapiintro.models.CourseMaterial;
import com.enigmacamp.restapiintro.models.CourseType;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseRequestDto;
import com.enigmacamp.restapiintro.repositories.interfaces.CourseRepository;
import com.enigmacamp.restapiintro.repositories.specifications.CourseSpecification;
import com.enigmacamp.restapiintro.services.interfaces.CourseService;
import com.enigmacamp.restapiintro.services.interfaces.CourseTypeService;
import com.enigmacamp.restapiintro.services.interfaces.FileService;
import com.enigmacamp.restapiintro.shared.classes.PagedResponse;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import com.enigmacamp.restapiintro.shared.utils.FileChecksumUtility;
import com.enigmacamp.restapiintro.shared.utils.SearchCriteria;
import com.enigmacamp.restapiintro.shared.utils.SearchOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private CourseTypeService courseTypeService;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Course create(CreateCourseRequestDto createCourseRequestDto) throws Exception {
        try {
            // Map dto data into model
            Course course = modelMapper.map(createCourseRequestDto, Course.class);
            Optional<CourseType> courseType = courseTypeService.getById(createCourseRequestDto.getCourseTypeId());
            courseType.ifPresent(course::setCourseType);
            CourseInfo courseInfo = new CourseInfo();
            courseInfo.setDuration(createCourseRequestDto.getDuration());
            courseInfo.setLevel(createCourseRequestDto.getLevel());
            course.setCourseInfo(courseInfo);

            if (
                    Objects.equals(createCourseRequestDto.getMaterialTitle(), "") ||
                            createCourseRequestDto.getMaterial() == null
            ) {
                return courseRepository.save(course);
            }

            // Save material file
            String savedPath = fileService.upload(createCourseRequestDto.getMaterial());
            Resource savedFile = fileService.download(savedPath);

            CourseMaterial courseMaterial = new CourseMaterial();
            courseMaterial.setTitle(createCourseRequestDto.getMaterialTitle());
            courseMaterial.setFilename(createCourseRequestDto.getMaterial().getOriginalFilename());
            courseMaterial.setFileHash(FileChecksumUtility.getMd5FileCheckSum(savedFile.getFile()));
            courseMaterial.setFileUrl(savedPath);

            course.getCourseMaterialList().add(courseMaterial);
            courseMaterial.setRelatedCourse(course);

            return courseRepository.save(course);
        } catch (Exception e) {
            String originalFilename = createCourseRequestDto.getMaterial().getOriginalFilename();
            if (!Objects.equals(originalFilename, "") && fileService.download(originalFilename) != null) {
                fileService.remove(originalFilename);
            }
            throw e;
        }
    }

    @Override
    public Page<Course> getAll(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Iterable<Course> getAll(Course filter, Boolean shouldMatchAll, Pageable pageable) {
        return null;
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

        existingCourse.get().setTitle(course.getTitle());
        existingCourse.get().setDescription(course.getDescription());
        existingCourse.get().setSlug(course.getSlug());
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
