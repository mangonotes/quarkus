package org.mangonotes.model.dto.res;

import org.mangonotes.model.dto.TodoBaseDTO;

import java.util.List;

public class TodoResDTO extends TodoBaseDTO {
    private Long id;
    private List<TaskResDTO> tasks;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public List<TaskResDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskResDTO> tasks) {
        this.tasks = tasks;
    }


}
