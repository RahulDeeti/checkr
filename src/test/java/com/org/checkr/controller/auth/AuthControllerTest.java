package com.org.checkr.controller.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.org.checkr.dto.AuthRequest;
import com.org.checkr.service.JwtService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

class AuthControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateAndGetTokenValidUser() {
        AuthRequest authRequest = new AuthRequest("testuser", "testpassword");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        when(jwtService.generateToken("testuser")).thenReturn("generated_token");

        String token = authController.authenticateAndGetToken(authRequest);

        assertEquals("generated_token", token);
    }

    @Test
    void testAuthenticateAndGetTokenInvalidUser() {
        AuthRequest authRequest = new AuthRequest("testuser", "testpassword");

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        String response = authController.authenticateAndGetToken(authRequest);

        assertEquals("Invalid user request, token is invalid", response);
    }
}

