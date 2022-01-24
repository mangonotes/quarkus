package org.mangonotes.services.todo;

import org.mangonotes.model.dto.req.TodoReqDTO;
import org.mangonotes.model.dto.res.TodoResDTO;

public interface ITodoCommandService {
    TodoResDTO create(TodoReqDTO reqDTO);
    void update(TodoReqDTO todoReqDTO, Long id);
    void delete(Long id);
}
