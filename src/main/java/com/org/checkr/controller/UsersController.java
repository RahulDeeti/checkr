package com.org.checkr.controller;

import com.org.checkr.dto.request.CreateUserRequestDTO;
import com.org.checkr.dto.response.CreateUserResponseDTO;
import com.org.checkr.entity.User;
import com.org.checkr.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        User user = userService.saveUser(createUserRequestDTO);
        CreateUserResponseDTO responseDTO = userService.mapUserToResponseDTO(user);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
