package com.javamentor.jm_spring_boot.repository;

import com.javamentor.jm_spring_boot.model.User;

public interface UserRepository extends GenericRepository<User> {

    User findByUsername(String username);

}
