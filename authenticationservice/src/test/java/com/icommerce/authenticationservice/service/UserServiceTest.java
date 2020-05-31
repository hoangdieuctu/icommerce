package com.icommerce.authenticationservice.service;

import com.icommerce.authenticationservice.model.User;
import com.icommerce.authenticationservice.repository.UserRepository;
import com.icommerce.authenticationservice.service.impl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    @Test
    public void testSaveUser() {
        userService.save(user);
        verify(userRepository).save(user);
    }

}
