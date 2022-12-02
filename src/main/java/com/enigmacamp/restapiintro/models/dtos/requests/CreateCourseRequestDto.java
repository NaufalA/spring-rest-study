package com.enigmacamp.restapiintro.models.dtos.requests;

import com.enigmacamp.restapiintro.models.CourseType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateCourseRequestDto {
    @NotBlank(message = "Title cannot be empty")
    private String title;
    @NotBlank(message = "Description cannot be empty")
    private String description;
    @NotBlank(message = "Link cannot be empty")
    private String slug;

    @NotNull(message = "Course Type cannot be empty")
    private CourseType courseType;

    @NotNull(message = "Course Info cannot be empty")
    private CreateCourseInfoRequestDto courseInfo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

    public CreateCourseInfoRequestDto getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CreateCourseInfoRequestDto courseInfo) {
        this.courseInfo = courseInfo;
    }
}