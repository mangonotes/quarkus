package org.mangonotes.model.dto.res;

import org.mangonotes.model.dto.TodoBaseDTO;

public class TaskResDTO extends TodoBaseDTO {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
