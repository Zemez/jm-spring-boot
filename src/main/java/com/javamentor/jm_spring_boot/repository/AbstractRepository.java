package com.javamentor.jm_spring_boot.repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public abstract class AbstractRepository<T> implements GenericRepository<T> {

    private static final String INVALID_NULL_ID = "Invalid null id.";
    private final String INVALID_NULL_ENTITY;

    private final Class<T> entityClass;
    private final EntityManager entityManager;

    public AbstractRepository(Class<T> entityClass, EntityManager entityManager) {
        this.entityClass = entityClass;
        this.entityManager = entityManager;
        INVALID_NULL_ENTITY = String.format("Invalid null %s.", entityClass.getName());
    }

    @Override
    public T create(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException(INVALID_NULL_ENTITY);
        }
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(INVALID_NULL_ID);
        }
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        TypedQuery<T> query = entityManager.createQuery("from " + entityClass.getName(), entityClass);
        return query.getResultList();
    }

    @Override
    public T update(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException(INVALID_NULL_ENTITY);
        }
        return entityManager.merge(entity);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(INVALID_NULL_ID);
        }
        T entity = entityManager.find(entityClass, id);
        entityManager.remove(entity);
    }

}
