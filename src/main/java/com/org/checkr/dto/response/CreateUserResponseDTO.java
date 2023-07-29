package com.org.checkr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateUserResponseDTO {
    private final Long id;
    private final String userName;
    private final String email;
}
