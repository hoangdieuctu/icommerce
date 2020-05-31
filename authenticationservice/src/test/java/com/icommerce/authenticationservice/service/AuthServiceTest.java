package com.icommerce.authenticationservice.service;

import com.icommerce.authenticationservice.dto.JwtDto;
import com.icommerce.authenticationservice.model.User;
import com.icommerce.authenticationservice.service.impl.AuthServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private User user;

    private int userId = 1;
    private String token = "token here";

    @Before
    public void setup() {
        when(user.getId()).thenReturn(userId);
        when(userService.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(userId)).thenReturn(token);
    }

    @Test
    public void testLoginByFacebook() {
        JwtDto jwtDto = authService.loginByFacebook();
        Assert.assertEquals(token, jwtDto.getToken());
    }

}
