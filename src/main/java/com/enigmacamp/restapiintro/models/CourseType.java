package com.enigmacamp.restapiintro.models;

import com.enigmacamp.restapiintro.shared.models.UUIDBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course_types")
public class CourseType extends UUIDBaseEntity {
    @Column(name = "type_name", nullable = false, unique = true, length = 100)
    private String typeName;

    @JsonIgnore
    @OneToMany(mappedBy = "courseType")
    private List<Course> courses;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "CourseType{" +
                "id='" + getId() + '\'' +
                "typeName='" + typeName + '\'' +
                '}';
    }
}