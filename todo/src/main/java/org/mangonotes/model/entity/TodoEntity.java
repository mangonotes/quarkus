package org.mangonotes.model.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="tb_todo")
public class TodoEntity  extends  BaseEntity<Long>{
@Column(length = 100)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @OneToMany(mappedBy="todo", cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch=FetchType.LAZY)
    private List<TaskEntity> tasks= new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void addTask(TaskEntity task){
        tasks.add(task);
        task.setTodo(this);
    }
    public void clearTask(){
        tasks.clear();
    }
    public void removeTask(TaskEntity task) {
        tasks.remove(task);
        task.setTodo(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoEntity that = (TodoEntity) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
