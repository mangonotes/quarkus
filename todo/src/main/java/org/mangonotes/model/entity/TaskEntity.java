package org.mangonotes.model.entity;

import javax.persistence.*;

@Entity
@Table(name="tb_task")
public class TaskEntity extends  BaseEntity<Long>{
@Column(length = 100)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId
    private TodoEntity todo;

    public TodoEntity getTodo() {
        return todo;
    }

    public void setTodo(TodoEntity todo) {
        this.todo = todo;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskEntity that = (TaskEntity) o;

        return this.getId() != null ? this.getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
