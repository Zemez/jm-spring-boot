package com.javamentor.jm_spring_boot.service;

import com.javamentor.jm_spring_boot.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends GenericService<User>, UserDetailsService {

    User findByUsername(String username);

}

