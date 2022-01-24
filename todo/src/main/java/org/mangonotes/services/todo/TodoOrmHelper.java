package org.mangonotes.services.todo;

import org.mangonotes.model.entity.TaskEntity;
import org.mangonotes.model.entity.TodoEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

@ApplicationScoped
public class TodoOrmHelper {
    @Inject
    EntityManager entityManager;

    public CriteriaQuery createFindById(Long id){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TodoEntity> query = cb.createQuery(TodoEntity.class);
        Root<TodoEntity> employee = query.from(TodoEntity.class);
        employee.fetch("tasks", JoinType.INNER);
        query.where(cb.equal(employee.get("id"), id));
        query.select(employee)
                .distinct(true);
       //System.out.println( entityManager.createQuery(query).getSingleResult());
        return  query;
    }
}
