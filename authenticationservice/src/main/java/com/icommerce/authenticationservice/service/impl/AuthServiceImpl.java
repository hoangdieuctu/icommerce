package com.icommerce.authenticationservice.service.impl;

import com.icommerce.authenticationservice.constant.Constants;
import com.icommerce.authenticationservice.dto.JwtDto;
import com.icommerce.authenticationservice.model.User;
import com.icommerce.authenticationservice.service.AuthService;
import com.icommerce.authenticationservice.service.JwtService;
import com.icommerce.authenticationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Override
    public JwtDto loginByFacebook() {
        User user = new User();
        user.setName("Facebook User");
        user.setCreatedTime(new Date());

        // this can call to other service if we want to manage user
        // in this scope, just generate an user and store db
        User savedUser = userService.save(user);
        String token = jwtService.generateToken(savedUser.getId());

        JwtDto jwtDto = new JwtDto();
        jwtDto.setToken(token);
        jwtDto.setSchema(Constants.AUTH_SCHEMA);
        return jwtDto;
    }

}
