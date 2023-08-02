package com.org.checkr.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.org.checkr.entity.User;
import com.org.checkr.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserAuthDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserAuthDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername_UserExists() {
        String userName = "testuser";
        User user = new User(userName, "user@email.com", "password" );

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        assertNotNull(userDetails);
        assertEquals(userName, userDetails.getUsername());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String userName = "I am not in the db";
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(userName));
    }
}

