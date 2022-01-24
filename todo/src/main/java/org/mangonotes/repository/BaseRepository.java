package org.mangonotes.repository;

public interface BaseRepository<PK, E> extends QueryRepository<PK,E>, CrudRepository<PK,E> {
}
