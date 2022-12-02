package com.enigmacamp.restapiintro.repositories.interfaces;

import com.enigmacamp.restapiintro.models.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseTypeRepository extends JpaRepository<CourseType, String> {
}
