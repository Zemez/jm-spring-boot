package com.javamentor.jm_spring_boot.repository;

import com.javamentor.jm_spring_boot.model.Role;

public interface RoleRepository extends GenericRepository<Role> {

    Role findByName(String name);

}
