package com.enigmacamp.restapiintro.models;

import com.enigmacamp.restapiintro.shared.models.UUIDBaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "course_materials")
public class CourseMaterial extends UUIDBaseEntity {
    @Column(name = "title", unique = true, nullable = false)
    private String title;
    @Column(name = "file_name", nullable = false)
    private String filename;
    @Column(name = "file_url", nullable = false)
    private String fileUrl;
    @Column(name = "file_hash", nullable = false)
    private String fileHash;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "related_course_id")
    private Course relatedCourse;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public Course getRelatedCourse() {
        return relatedCourse;
    }

    public void setRelatedCourse(Course relatedCourse) {
        this.relatedCourse = relatedCourse;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
