package com.javamentor.jm_spring_boot.service;

import com.javamentor.jm_spring_boot.model.Authority;
import com.javamentor.jm_spring_boot.repository.AuthorityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("authorityService")
@Transactional(readOnly = true)
public class AuthorityServiceImpl extends AbstractService<Authority> implements AuthorityService {

//    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        super(Authority.class, authorityRepository);
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority findByName(String name) {
        return authorityRepository.findByName(name);
    }

}
