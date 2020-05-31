package com.icommerce.authenticationservice.controller;

import com.icommerce.authenticationservice.dto.JwtDto;
import com.icommerce.authenticationservice.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @ResponseBody
    @GetMapping("/login/facebook")
    public JwtDto loginByFacebook() {
        JwtDto jwtDto = authService.loginByFacebook();
        logger.debug("Facebook jwt: {}", jwtDto);

        return jwtDto;
    }

}
