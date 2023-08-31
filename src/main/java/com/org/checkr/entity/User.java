package com.org.checkr.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable = false, name = "name")
    @NotBlank(message = "User name must not be empty")
    private String userName;

    @Column(unique=true, nullable = false)
    @NotBlank(message = "email must not be empty")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "password must not be empty")
    private String password;

    public User(String name, String email, String password) {
        this.userName = name;
        this.email = email;
        this.password = password;
    }
}
