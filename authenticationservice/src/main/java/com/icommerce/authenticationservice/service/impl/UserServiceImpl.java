package com.icommerce.authenticationservice.service.impl;

import com.icommerce.authenticationservice.model.User;
import com.icommerce.authenticationservice.repository.UserRepository;
import com.icommerce.authenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
