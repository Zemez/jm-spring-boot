package com.javamentor.jm_spring_boot.service;

import com.javamentor.jm_spring_boot.model.Role;

public interface RoleService extends GenericService<Role> {

    Role findByName(String name);

}
