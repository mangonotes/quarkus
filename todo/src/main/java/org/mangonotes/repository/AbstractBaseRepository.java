package org.mangonotes.repository;

import jdk.jshell.spi.ExecutionControl;
import org.apache.commons.lang3.NotImplementedException;
import org.mangonotes.model.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class AbstractBaseRepository<PK extends Serializable, E extends BaseEntity<PK>> implements BaseRepository<PK, E> {
    private final Logger logger = LoggerFactory.getLogger(AbstractBaseRepository.class);

    protected abstract Class<E> getPersistentClass();

    protected abstract EntityManager getEntityManager();

    @Override
    public E insertOrSave(E entity) {

        if (entity.isNew()) {
            getEntityManager().persist(entity);
        } else {
            getEntityManager().merge(entity);
        }
        return entity;

    }

    @Override
    public boolean delete(E entity) {
        getEntityManager().remove(entity);
        return Boolean.TRUE;
    }

    @Override
    public List<E> query(Query query) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<E> findById(PK id) {

        return Optional.ofNullable(getEntityManager().find(getPersistentClass(), id));
    }

    @Override
    public List<E> queryAll(String query) {
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public Optional<E> query(CriteriaQuery<E> query) {
        try {
            return Optional.ofNullable(getEntityManager().createQuery(query).getSingleResult());
        } catch (NoResultException e) {
            logger.error("no result ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<E> findAll() {
        List<E> result = getEntityManager().createQuery("Select t from " + getPersistentClass().getSimpleName() + " t").getResultList();
        if (result == null || result.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return result;
    }
}
