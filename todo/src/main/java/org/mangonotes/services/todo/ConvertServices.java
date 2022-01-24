package org.mangonotes.services.todo;

import org.mangonotes.model.dto.req.TaskReqDTO;
import org.mangonotes.model.dto.res.TaskResDTO;
import org.mangonotes.model.dto.req.TodoReqDTO;
import org.mangonotes.model.dto.res.TodoResDTO;
import org.mangonotes.model.entity.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

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


}
