package com.enigmacamp.restapiintro.repositories;

import com.enigmacamp.restapiintro.models.Course;
import com.enigmacamp.restapiintro.shared.exceptions.NotFoundException;
import com.enigmacamp.restapiintro.shared.utils.IRandomStringGenerator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CourseRepositoryImpl implements CourseRepository {
    private List<Course> courses;
    private IRandomStringGenerator randomStringGenerator;

    public CourseRepositoryImpl(IRandomStringGenerator randomStringGenerator) {
        this.randomStringGenerator = randomStringGenerator;
        this.courses = new ArrayList<>();
    }

    @Override
    public Course insert(Course course) {
        course.setId(randomStringGenerator.get());
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

    @Override
    public List<Course> findAll(String field, String value) {
        List<Course> result = new ArrayList<>();
        switch (field) {
            case "title":
                result = courses.stream()
                        .filter(c -> c.getTitle().toLowerCase().contains(value.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            case "description":
                result = courses.stream()
                        .filter(c -> c.getDescription().toLowerCase().contains(value.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            case "slug":
                result = courses.stream()
                        .filter(c -> c.getSlug().toLowerCase().contains(value.toLowerCase()))
                        .collect(Collectors.toList());
                break;
        }

        return result;
    }
}
