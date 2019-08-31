package com.javamentor.jm_spring_boot.service;

import com.javamentor.jm_spring_boot.model.Authority;
import com.javamentor.jm_spring_boot.model.User;
import com.javamentor.jm_spring_boot.repository.AuthorityRepository;
import com.javamentor.jm_spring_boot.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl extends AbstractService<User> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    private final Map<String, Long> authorityCache = new HashMap<>();

    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository) {
        super(User.class, userRepository);
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    @Transactional
    public User create(User user) {
        if (user.getId() != null) user.setId(null);
        identifyAuthorities(user);
        return userRepository.create(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        identifyAuthorities(user);
        if (userRepository.findById(user.getId()) == null) throw  new IllegalArgumentException("Invalid user.");
        return userRepository.update(user);
    }

    @Override
    public User loadUserByUsername(String username) throws IllegalStateException {
        return userRepository.findByUsername(username);
    }

    private void identifyAuthorities(User user) {
        for (Authority authority : user.getAuthorities()) {
            if (authority.getId() == null) {
                String name = authority.getAuthority();
                if (authorityCache.containsKey(name)) {
                    authority.setId(authorityCache.get(name));
                } else {
                    Authority exists = authorityRepository.findByName(name);
                    if (exists != null) {
                        Long id = exists.getId();
                        authority.setId(id);
                        authorityCache.put(name, id);
                    }
                }
            }
            logger.debug("cache: {}", authorityCache);
        }
    }

}
