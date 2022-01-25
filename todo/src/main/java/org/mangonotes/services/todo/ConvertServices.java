package org.mangonotes.services.todo;

import org.apache.commons.lang3.StringUtils;
import org.mangonotes.model.dto.req.TaskReqDTO;
import org.mangonotes.model.dto.req.TodoReqDTO;
import org.mangonotes.model.dto.res.TaskResDTO;
import org.mangonotes.model.dto.res.TodoResDTO;
import org.mangonotes.model.entity.TaskEntity;
import org.mangonotes.model.entity.TodoEntity;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ApplicationScoped
public class ConvertServices {
    private Logger logger= LoggerFactory.getLogger(ConvertServices.class);
    private final ModelMapper modelMapper;
    /*@PostConstruct
    void  setup(){
        modelMapper.createTypeMap(TodoReqDTO.class, TodoEntity.class).
                addMapping(TodoReqDTO::getTasks, TodoEntity::setTasks);
    }*/

    @Inject
    public ConvertServices(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T, E> E convert(T from, Class<E> type) {
        return modelMapper.map(from, type);
    }

    public TodoEntity convert(TodoReqDTO reqDTO) {
        TodoEntity todoEntity = convert(reqDTO, TodoEntity.class);
        reqDTO.getTasks().stream()
                .map(this::convert)
                .peek(todoEntity::addTask)
                .collect(Collectors.toList());
        return todoEntity;
    }

    public TaskEntity convert(TaskReqDTO taskReqDTO) {
        return convert(taskReqDTO, TaskEntity.class);
    }
    public TaskResDTO convert(TaskEntity entity){
        return convert(entity, TaskResDTO.class);
    }

    public TodoResDTO convert(TodoEntity todoEntity) {
        TodoResDTO todoResDTO= convert(todoEntity, TodoResDTO.class);
        List<TaskResDTO> tasks= todoEntity.getTasks()
                .stream()
                .map(this::convert)
                .collect(Collectors.toList());
       todoResDTO.setTasks( tasks);
       return  todoResDTO;
    }
    public TaskEntity convert(TaskEntity entity, TaskReqDTO reqDTO){
        entity.setName(reqDTO.getName());
        entity.setDescription(reqDTO.getDescription());
        return entity;
    }

    public void  convert(TodoEntity todoEntity, TodoReqDTO todoReqDTO) {
        todoEntity.setName(todoReqDTO.getName());
        todoEntity.setDescription(todoReqDTO.getDescription());
        // both array equals
        IntStream.range(0, todoEntity.getTasks().size())
                .filter(i -> todoReqDTO.getTasks().size() > i&& todoEntity.getTasks().size()>i)
                .forEach(i -> {
                    logger.info("todoEntity.getTasks().get(i) {} {}", i,  todoEntity.getTasks().get(i));
                    convert(todoEntity.getTasks().get(i), todoReqDTO.getTasks().get(i));
                });
        //right array
        if (todoEntity.getTasks().size() < todoReqDTO.getTasks().size()) {
       IntStream.range(todoEntity.getTasks().size(), todoReqDTO.getTasks().size())
                    .forEach(i -> {
                        logger.info("addtask {}", todoReqDTO.getTasks().get(i));
                        logger.info("converted{}", convert(todoReqDTO.getTasks().get(i)));
                        todoEntity.addTask(convert(todoReqDTO.getTasks().get(i)));
                    });

        }
        //left array
        else {
            IntStream.range(todoReqDTO.getTasks().size(), todoEntity.getTasks().size()).forEach(i -> {
                todoEntity.removeTask(todoEntity.getTasks().get(i));
            });
        }
     //   logger.info("tasks {}", StringUtil.join(todoEntity.getTasks(), ));
        logger.info("tasks {}",StringUtils.join(todoEntity.getTasks(), "|"));
    }


}
