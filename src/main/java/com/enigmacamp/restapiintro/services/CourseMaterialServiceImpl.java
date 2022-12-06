package com.enigmacamp.restapiintro.services;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.models.CourseMaterial;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseMaterialRequestDto;
import com.enigmacamp.restapiintro.repositories.interfaces.CourseMaterialRepository;
import com.enigmacamp.restapiintro.services.interfaces.CourseMaterialService;
import com.enigmacamp.restapiintro.services.interfaces.CourseService;
import com.enigmacamp.restapiintro.services.interfaces.FileService;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import com.enigmacamp.restapiintro.shared.utils.FileChecksumUtility;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseMaterialServiceImpl implements CourseMaterialService {
    private final CourseMaterialRepository courseMaterialRepository;
    private final FileService fileService;
    private final CourseService courseService;

    public CourseMaterialServiceImpl(
            CourseMaterialRepository courseMaterialRepository,
            FileService fileService,
            CourseService courseService
    ) {
        this.courseMaterialRepository = courseMaterialRepository;
        this.fileService = fileService;
        this.courseService = courseService;
    }

    @Override
    public Resource downloadMaterial(String id) {
        Optional<CourseMaterial> courseMaterial = courseMaterialRepository.findById(id);
        if (courseMaterial.isPresent()) {
            return fileService.download(courseMaterial.get().getFileUrl());
        } else {
            throw new NotFoundException("Course Material Not Found");
        }
    }

    @Override
    public List<CourseMaterial> getByCourseId(String courseId) {
        Optional<Course> course = courseService.getById(courseId);
        if (course.isPresent()) {
            return courseMaterialRepository.findAll().stream()
                    .filter(cm -> cm.getRelatedCourse().getId().equals(course.get().getId()))
                    .collect(Collectors.toList());
        } else {
            throw new NotFoundException("Couldn't Get Materials, Course Data Not Found");
        }
    }

    @Override
    @Transactional
    public CourseMaterial addMaterial(CreateCourseMaterialRequestDto dto) throws Exception {
        try {
            if (courseMaterialRepository.findByTitle(dto.getTitle()).isPresent()) {
                throw new RuntimeException("Material With the Same Title Already Exist");
            }

            // Save material file
            String savedPath = fileService.upload(dto.getFile());
            Resource savedFile = fileService.download(savedPath);

            Optional<Course> course = courseService.getById(dto.getCourseId());

            if (course.isEmpty()) {
                throw new NotFoundException("Couldn't Add Material, Course Data Not Found");
            }

            CourseMaterial courseMaterial = new CourseMaterial();
            courseMaterial.setTitle(dto.getTitle());
            courseMaterial.setFilename(dto.getFile().getOriginalFilename());
            courseMaterial.setFileHash(FileChecksumUtility.getMd5FileCheckSum(savedFile.getFile()));
            courseMaterial.setFileUrl(savedPath);
            courseMaterial.setRelatedCourse(course.get());

            return courseMaterialRepository.save(courseMaterial);
        } catch (Exception e) {
            if (fileService.download(dto.getFile().getOriginalFilename()) != null) {
                fileService.remove(dto.getFile().getOriginalFilename());
            }
            throw e;
        }
    }
}
