package com.icommerce.authenticationservice.controller;

import com.icommerce.authenticationservice.constant.Constants;
import com.icommerce.authenticationservice.dto.JwtDto;
import com.icommerce.authenticationservice.service.AuthService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    private MockMvc mockMvc;

    private JwtDto jwtDto;

    private String token = "token here";

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();

        jwtDto = new JwtDto();
        jwtDto.setToken(token);
        jwtDto.setSchema(Constants.AUTH_SCHEMA);

        when(authService.loginByFacebook()).thenReturn(jwtDto);
    }

    @Test
    public void testLoginByFacebook() throws Exception {
        this.mockMvc.perform(get("/api/v1/auth/login/facebook"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", Matchers.is(token)))
                .andExpect(jsonPath("$.schema", Matchers.is(Constants.AUTH_SCHEMA)));
    }

}
