package com.enigmacamp.restapiintro.models.dtos.requests;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateCourseRequestDto {
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    private String description;

    @NotBlank(message = "Link cannot be empty")
    private String slug;

    @NotBlank(message = "Course Type cannot be empty")
    private String courseTypeId;

    @NotNull(message = "Course Duration cannot be empty")
    private Integer duration;

    @NotNull(message = "Course Level cannot be empty")
    private Integer level;

    private String materialTitle;
    @NotNull(message = "Material cannot be empty")
    private MultipartFile material;

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

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMaterialTitle() {
        return materialTitle;
    }

    public void setMaterialTitle(String materialTitle) {
        this.materialTitle = materialTitle;
    }

    public MultipartFile getMaterial() {
        return material;
    }

    public void setMaterial(MultipartFile material) {
        this.material = material;
    }
}