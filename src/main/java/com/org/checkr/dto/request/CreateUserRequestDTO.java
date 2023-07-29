package com.org.checkr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class CreateUserRequestDTO {
    private final String userName;
    private final String email;
    private final String password;
}
