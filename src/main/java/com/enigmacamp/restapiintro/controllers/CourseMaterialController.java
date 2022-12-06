package com.enigmacamp.restapiintro.controllers;

import com.enigmacamp.restapiintro.models.CourseMaterial;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseMaterialRequestDto;
import com.enigmacamp.restapiintro.services.interfaces.CourseMaterialService;
import com.enigmacamp.restapiintro.shared.classes.CommonResponse;
import com.enigmacamp.restapiintro.shared.classes.SuccessResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course-material")
public class CourseMaterialController {
    private final CourseMaterialService courseMaterialService;

    public CourseMaterialController(CourseMaterialService courseMaterialService) {
        this.courseMaterialService = courseMaterialService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse> createMaterial(CreateCourseMaterialRequestDto dto) throws Exception {
        CourseMaterial courseMaterial = courseMaterialService.addMaterial(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.toString(),
                "Course Material Created Successfully",
                courseMaterial
        ));
    }

    @GetMapping("/list")
    public ResponseEntity<CommonResponse> getAllByCourse(@RequestParam("courseId") String courseId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(
                HttpStatus.CREATED.toString(),
                "Course Materials Fetched Successfully",
                courseMaterialService.getByCourseId(courseId)
        ));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadMaterial(@RequestParam("id") String id) {
        Resource materialFile = courseMaterialService.downloadMaterial(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + materialFile.getFilename() + "\"")
                .body(materialFile);
    }
}
