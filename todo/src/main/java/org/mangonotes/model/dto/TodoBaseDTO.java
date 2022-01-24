package org.mangonotes.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TodoBaseDTO {
    @NotBlank(message="Name should not be blank")
    private String name;
    @NotBlank(message="Description should not be blank")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
