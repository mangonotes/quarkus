package org.mangonotes.repository;

import org.mangonotes.model.entity.TodoEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
@ApplicationScoped
public class TodoRepository extends  AbstractBaseRepository<Long, TodoEntity> {
    @Inject
    EntityManager entityManager;
    @Override
    protected Class<TodoEntity> getPersistentClass() {
        return TodoEntity.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
