package com.javamentor.jm_spring_boot.service;

import com.javamentor.jm_spring_boot.model.Generic;
import com.javamentor.jm_spring_boot.repository.GenericRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class AbstractService<T extends Generic> implements GenericService<T> {

    private final Class<T> entityClass;
    private final GenericRepository<T> repository;

    public AbstractService(Class<T> entityClass, GenericRepository<T> repository) {
        this.entityClass = entityClass;
        this.repository = repository;
    }

    @Override
    @Transactional
    public T create(T entity) {
        if (entity.getId() != null) {
            entity.setId(null);
        }
        return repository.create(entity);
    }

    @Override
    public T findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public T update(T entity) {
        if (repository.findById(entity.getId()) == null) {
            throw new IllegalArgumentException(String.format("Invalid %s.", entityClass.getName()));
        }
        return repository.update(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
