package com.javamentor.jm_spring_boot.repository;

import com.javamentor.jm_spring_boot.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Repository("userRepository")
public class UserRepositoryImpl extends AbstractRepository<User> implements UserRepository {

    private final EntityManager entityManager;

    public UserRepositoryImpl(EntityManager entityManager) {
        super(User.class, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public User findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Invalid null username.");
        //noinspection JpaQlInspection
        TypedQuery<User> query = entityManager.createQuery("from User u where u.username = :username", User.class );
        query.setParameter("username", username);
        return query.getSingleResult();
    }

}
