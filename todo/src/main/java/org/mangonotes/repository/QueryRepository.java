package org.mangonotes.repository;


import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public interface QueryRepository<PK,E> {
    List<E> query(Query query);
    Optional<E> findById(PK id);
    List<E> queryAll(String query);
    Optional<E> query(CriteriaQuery<E> query);
    List<E> findAll();
}
