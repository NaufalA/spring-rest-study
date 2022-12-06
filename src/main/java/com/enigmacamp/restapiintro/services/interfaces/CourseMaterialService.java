package com.enigmacamp.restapiintro.services.interfaces;

import com.enigmacamp.restapiintro.models.CourseMaterial;
import com.enigmacamp.restapiintro.models.dtos.requests.CreateCourseMaterialRequestDto;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

public interface CourseMaterialService {
    Resource downloadMaterial(String id);
    List<CourseMaterial> getByCourseId(String courseId);
    CourseMaterial addMaterial(CreateCourseMaterialRequestDto dto) throws IOException, Exception;
}
