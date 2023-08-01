package com.org.checkr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.checkr.dto.request.CreateUserRequestDTO;
import com.org.checkr.dto.response.CreateUserResponseDTO;
import com.org.checkr.entity.User;
import com.org.checkr.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UsersControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        CreateUserRequestDTO requestDTO = new CreateUserRequestDTO("john doe", "john@email.com", "ab21");

        CreateUserResponseDTO responseDTO = new CreateUserResponseDTO(1L, "john doe", "john@email.com");

        User user = new User("john doe", "john@email.com", "ab21" );

        when(userService.saveUser(any(CreateUserRequestDTO.class))).thenReturn(user);
        when(userService.mapUserToResponseDTO(user)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userName").value(responseDTO.getUserName()))
                .andExpect(jsonPath("$.email").value(responseDTO.getEmail()));

        verify(userService).saveUser(requestDTO);
        verify(userService).mapUserToResponseDTO(user);
    }

    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

