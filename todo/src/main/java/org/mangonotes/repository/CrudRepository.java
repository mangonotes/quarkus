package org.mangonotes.repository;

public interface CrudRepository<PK, E> {
    E insertOrSave(E entity);
    boolean delete(E id);
}
