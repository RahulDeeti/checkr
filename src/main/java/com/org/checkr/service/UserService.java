package com.org.checkr.service;

import com.org.checkr.dto.request.CreateUserRequestDTO;
import com.org.checkr.dto.response.CreateUserResponseDTO;
import com.org.checkr.entity.Candidate;
import com.org.checkr.entity.User;
import com.org.checkr.exception.NotFoundException;
import com.org.checkr.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            throw new NotFoundException("Entity with ID " + id + " not found.");
        } else {
            return user;
        }
    }

    public User saveUser(CreateUserRequestDTO userRequestDTO) {
        User newUser =  new User(
                userRequestDTO.getUserName(),
                userRequestDTO.getEmail(),
                passwordEncoder.encode(userRequestDTO.getPassword()));

        return userRepository.save(newUser);
    }

    public CreateUserResponseDTO mapUserToResponseDTO(User user) {
        return new CreateUserResponseDTO(
                user.getId(),
                user.getUserName(),
                user.getEmail()
        );
    }
}