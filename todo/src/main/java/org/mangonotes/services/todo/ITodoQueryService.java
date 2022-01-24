package org.mangonotes.services.todo;

import org.mangonotes.model.dto.res.TodoResDTO;

import java.util.List;
import java.util.Optional;

public interface ITodoQueryService {
    List<TodoResDTO> getAll();
    TodoResDTO findById(Long id);

}
