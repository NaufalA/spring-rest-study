package com.enigmacamp.restapiintro.models;

import com.enigmacamp.restapiintro.shared.models.UUIDBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "course_infos")
public class CourseInfo extends UUIDBaseEntity {
    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "level", nullable = false)
    private Integer level;

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
}