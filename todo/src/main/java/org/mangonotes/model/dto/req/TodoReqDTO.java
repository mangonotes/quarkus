package org.mangonotes.model.dto.req;

import org.mangonotes.model.dto.TodoBaseDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

public class TodoReqDTO  extends TodoBaseDTO {
    @Valid
    @NotNull
    @Size(min=1,message = "Tasks should not be empty")
    private List<TaskReqDTO> tasks;

    public List<TaskReqDTO> getTasks() {
        if (tasks == null){
            return Collections.EMPTY_LIST;
        }
        return tasks;
    }

    public void setTasks(List<TaskReqDTO> tasks) {
        this.tasks = tasks;
    }

}
