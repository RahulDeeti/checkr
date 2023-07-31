package com.org.checkr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.org.checkr.dto.request.CreateUserRequestDTO;
import com.org.checkr.dto.response.CreateUserResponseDTO;
import com.org.checkr.entity.User;
import com.org.checkr.exception.NotFoundException;
import com.org.checkr.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {

    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void testGetUserById_ExistingId_ReturnsUser() {
        Long userId = 1L;
        User user = new User("JaneDoe", "jane.doe@example.com", "hashedPassword");
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        User result = userService.getUserById(userId);
        assertEquals(user, result);
    }

    @Test
    public void testGetUserById_NonExistingId_ThrowsNotFoundException() {
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.empty());
        assertThrows(NotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    public void testSaveUser_ValidUserRequestDTO_ReturnsSavedUser() {
        CreateUserRequestDTO requestDTO = new CreateUserRequestDTO("JaneDoe", "jane.doe@example.com", "password123");
        when(passwordEncoder.encode(requestDTO.getPassword())).thenReturn("hashedPassword");
        User savedUser = new User("JaneDoe", "jane.doe@example.com", "hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.saveUser(requestDTO);
        assertEquals(savedUser, result);
    }

    @Test
    public void testMapUserToResponseDTO_ValidUser_ReturnsResponseDTO() {
        User user = new User("JaneDoe", "jane.doe@example.com", "hashedPassword");
        CreateUserResponseDTO responseDTO = userService.mapUserToResponseDTO(user);

        assertEquals(user.getId(), responseDTO.getId());
        assertEquals(user.getUserName(), responseDTO.getUserName());
        assertEquals(user.getEmail(), responseDTO.getEmail());
    }
}

