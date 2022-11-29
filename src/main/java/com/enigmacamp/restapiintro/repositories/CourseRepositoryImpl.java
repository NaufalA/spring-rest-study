package com.enigmacamp.restapiintro.repositories;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CourseRepositoryImpl implements CourseRepository {
    private List<Course> courses;

    public CourseRepositoryImpl() {
        this.courses = new ArrayList<>();
    }

    @Override
    public Course insert(Course course) {
        course.setId(UUID.randomUUID().toString());

        courses.add(course);
        return course;
    }

    @Override
    public List<Course> findAll() {
        return courses;
    }

    @Override
    public Optional<Course> findById(String id) {
        for (Course c : courses) {
            if (c.getId().equals(id)) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

    @Override
    public Course update(String id, Course course) {
        Optional<Course> exsistingCourse = findById(id);
        if (exsistingCourse.isPresent()) {
            exsistingCourse.get().setTitle(course.getTitle());
            exsistingCourse.get().setDescription(course.getDescription());
            exsistingCourse.get().setSlug(course.getSlug());
            courses.set(courses.indexOf(exsistingCourse.get()), exsistingCourse.get());
            return exsistingCourse.get();
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public String delete(String id) {
        Optional<Course> exsistingCourse = findById(id);
        if (exsistingCourse.isPresent()) {
            courses.remove(exsistingCourse.get());
            return id;
        } else {
            throw new NotFoundException();
        }
    }
}
