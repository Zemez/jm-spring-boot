package com.javamentor.jm_spring_boot.repository;

import com.javamentor.jm_spring_boot.model.Authority;

public interface AuthorityRepository extends GenericRepository<Authority> {

    Authority findByName(String name);

}
