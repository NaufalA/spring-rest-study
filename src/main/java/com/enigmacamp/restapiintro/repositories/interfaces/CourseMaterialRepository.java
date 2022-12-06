package com.enigmacamp.restapiintro.repositories.interfaces;

import com.enigmacamp.restapiintro.models.CourseMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, String> {
    Optional<CourseMaterial> findByTitle(String title);
}
