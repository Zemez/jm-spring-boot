package com.javamentor.jm_spring_boot.service;

import com.javamentor.jm_spring_boot.model.Authority;

public interface AuthorityService extends GenericService<Authority> {

    Authority findByName(String name);

}
