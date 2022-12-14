package com.enigmacamp.restapiintro.models;

import com.enigmacamp.restapiintro.shared.models.UUIDBaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course extends UUIDBaseEntity {
    @Column(name = "title", nullable = false, length = 150, unique = true)
    private String title;
    @Column(name = "description", nullable = false, length = 250)
    private String description;
    @Column(name = "slug", nullable = false, length = 200, unique = true)
    private String slug;
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "course_type_id")
    private CourseType courseType;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "course_info_id")
    private CourseInfo courseInfo;

    @OneToMany(mappedBy = "relatedCourse", cascade = CascadeType.ALL)
    private List<CourseMaterial> courseMaterialList = new ArrayList<>();

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }


    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }

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

    public List<CourseMaterial> getCourseMaterialList() {
        return courseMaterialList;
    }

    public void setCourseMaterialList(List<CourseMaterial> courseMaterialList) {
        this.courseMaterialList = courseMaterialList;
    }
}
