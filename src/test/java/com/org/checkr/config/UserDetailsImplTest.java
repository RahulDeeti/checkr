package com.org.checkr.config;

import static org.junit.jupiter.api.Assertions.*;

import com.org.checkr.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

class UserDetailsImplTest {

    @Test
    void testUserDetailsImpl() {
        User user = new User();
        user.setUserName("testuser");
        user.setPassword("testpassword");

        UserDetails userDetails = new UserDetailsImpl(user);

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("testpassword", userDetails.getPassword());

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        assertTrue(authorities.isEmpty());

        assertTrue(userDetails.isAccountNonExpired());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.isCredentialsNonExpired());
        assertTrue(userDetails.isEnabled());
    }
}

