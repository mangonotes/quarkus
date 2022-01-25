package org.mangonotes.services.todo;

import org.mangonotes.model.entity.TodoEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

@ApplicationScoped
public class TodoOrmHelper {
    @Inject
    EntityManager entityManager;

    public CriteriaQuery<TodoEntity> createFindById(Long id) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TodoEntity> query = cb.createQuery(TodoEntity.class);
        Root<TodoEntity> employee = query.from(TodoEntity.class);
        employee.fetch("tasks", JoinType.INNER);
        query.where(cb.equal(employee.get("id"), id));
        query.select(employee)
                .distinct(true);
        return query;
    }

    public <T> void detach(T entity) {
        entityManager.detach(entity);
    }
}
