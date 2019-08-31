package com.javamentor.jm_spring_boot.repository;

import com.javamentor.jm_spring_boot.model.Authority;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository("authorityRepository")
public class AuthorityRepositoryImpl extends AbstractRepository<Authority> implements AuthorityRepository{

    private final EntityManager entityManager;

    public AuthorityRepositoryImpl(EntityManager entityManager) {
        super(Authority.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public Authority findByName(String name) {
        //noinspection JpaQlInspection
        TypedQuery<Authority> query = entityManager.createQuery("from Authority where authority = :name", Authority.class );
        query.setParameter("name", name);
        return query.getSingleResult();
    }

}
