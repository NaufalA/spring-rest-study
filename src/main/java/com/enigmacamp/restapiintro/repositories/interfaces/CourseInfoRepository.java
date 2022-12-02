package com.enigmacamp.restapiintro.repositories.interfaces;

import com.enigmacamp.restapiintro.models.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseInfoRepository extends JpaRepository<CourseInfo, String> {
}
