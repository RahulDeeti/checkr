package com.org.checkr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.jsonwebtoken.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.Key;
import java.util.Date;

class JwtServiceTest {

    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(jwtService, "SECRET", "e80XCtxHN9Siqh8S2IGlkeCmN0u8mImstqTNWBq64vJv2m1XY6RKCFQW6K0muoNQ");
    }

    @Test
    void testGenerateToken() {
        String userName = "testuser";
        String token = jwtService.generateToken(userName);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testExtractUsername() {
        String userName = "testuser";
        String token = jwtService.generateToken(userName);
        String extractedUserName = jwtService.extractUsername(token);
        assertEquals(userName, extractedUserName);
    }

    @Test
    void testExtractExpiration() {
        String userName = "testuser";
        String token = jwtService.generateToken(userName);
        Date expiration = jwtService.extractExpiration(token);
        assertNotNull(expiration);
    }

    @Test
    void testValidateTokenWithValidToken() {
        String userName = "testuser";
        String token = jwtService.generateToken(userName);

        when(userDetails.getUsername()).thenReturn(userName);
        assertTrue(jwtService.validateToken(token, userDetails));
    }

    @Test
    void testValidateTokenWithInvalidToken() {
        String userName = "testuser";
        String token = jwtService.generateToken(userName);

        // Creating a mock UserDetails with a different username
        UserDetails invalidUserDetails = mock(UserDetails.class);
        when(invalidUserDetails.getUsername()).thenReturn("otheruser");

        assertFalse(jwtService.validateToken(token, invalidUserDetails));
    }

    @Test
    void testValidateTokenWithExpiredToken() {
        String userName = "testuser";
        String token = generateExpiredToken(userName);

        when(userDetails.getUsername()).thenReturn(userName);
        assertThrows(ExpiredJwtException.class, () -> jwtService.validateToken(token, userDetails));
    }

    private String generateExpiredToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24)) // 24 hours ago
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60)) // 1 minute ago (expired)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Helper method to access the private getSignKey method using reflection
    private Key getSignKey() {
        try {
            Method method = JwtService.class.getDeclaredMethod("getSignKey");
            method.setAccessible(true);
            return (Key) method.invoke(jwtService);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail("Failed to access the private method getSignKey.");
        }
        return null;
    }
}

