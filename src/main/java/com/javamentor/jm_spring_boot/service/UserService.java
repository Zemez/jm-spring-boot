package com.javamentor.jm_spring_boot.service;

import com.javamentor.jm_spring_boot.model.User;

public interface UserService extends GenericService<User> {

    User loadUserByUsername(String username);

}

