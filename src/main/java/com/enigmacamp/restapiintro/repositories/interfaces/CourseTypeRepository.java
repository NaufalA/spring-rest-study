package com.enigmacamp.restapiintro.repositories.interfaces;

import com.enigmacamp.restapiintro.models.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseTypeRepository extends JpaRepository<CourseType, String> {
    Optional<CourseType> findByTypeName(String typeName);
}
