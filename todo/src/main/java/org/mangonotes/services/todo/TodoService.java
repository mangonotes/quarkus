package org.mangonotes.services.todo;

import org.mangonotes.exception.NotFoundException;
import org.mangonotes.model.dto.req.TodoReqDTO;
import org.mangonotes.model.dto.res.TodoResDTO;
import org.mangonotes.model.entity.TodoEntity;
import org.mangonotes.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class TodoService implements ITodoCommandService, ITodoQueryService {

    private final BaseRepository<Long, TodoEntity> todoRepository;
    private final ConvertServices convertServices;
    @Inject
    TodoOrmHelper todoOrmHelper;
    private final Logger logger = LoggerFactory.getLogger(TodoService.class);

    @Inject
    public TodoService(BaseRepository<Long, TodoEntity> todoRepository, ConvertServices convertServices) {
        this.todoRepository = todoRepository;
        this.convertServices = convertServices;
    }

    @Transactional
    @Override
    public TodoResDTO create(TodoReqDTO reqDTO) {
        TodoEntity entityToSave = convertServices.convert(reqDTO);
        TodoEntity entitySaved = todoRepository.insertOrSave(entityToSave);
        logger.info("saved {}", entitySaved.getId());
        return convertServices.convert(entitySaved);
    }

    @Transactional
    @Override
    public void update(TodoReqDTO todoReqDTO, Long id) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<TodoResDTO> getAll() {
        logger.info("tod repository{}" + todoRepository);
        return todoRepository.queryAll("Select t from TodoEntity t JOIN FETCH t.tasks").stream()
                .map(convertServices::convert)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResDTO findById(Long id) {
        CriteriaQuery query = todoOrmHelper.createFindById(id);
        Optional<TodoEntity> optionalTodo = todoRepository.query(query);
        optionalTodo.orElseThrow(() -> new NotFoundException("Not find for id " + id));
        return convertServices.convert(optionalTodo.get());
    }

}
