package com.enigmacamp.restapiintro.models.dtos.requests;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CreateCourseInfoRequestDto {
    @NotNull(message = "Duration cannot be empty")
    @Min(value = 10, message = "Duration should be at least 10")
    private Integer duration;

    @NotNull(message = "Duration cannot be empty")
    @Min(value = 1, message = "Level should be at least 1")
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
