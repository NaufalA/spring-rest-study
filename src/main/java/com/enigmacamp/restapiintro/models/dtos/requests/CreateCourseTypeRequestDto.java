package com.enigmacamp.restapiintro.models.dtos.requests;

import javax.validation.constraints.NotBlank;

public class CreateCourseTypeRequestDto {
    @NotBlank
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
