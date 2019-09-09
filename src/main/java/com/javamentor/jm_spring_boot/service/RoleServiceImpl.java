package com.javamentor.jm_spring_boot.service;

import com.javamentor.jm_spring_boot.model.Role;
import com.javamentor.jm_spring_boot.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("roleService")
@Transactional(readOnly = true)
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {

//    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

}
