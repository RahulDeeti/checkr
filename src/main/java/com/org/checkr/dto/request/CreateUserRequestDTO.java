package com.org.checkr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserRequestDTO {
    private final String userName;
    private final String email;
    private final String password;
}
