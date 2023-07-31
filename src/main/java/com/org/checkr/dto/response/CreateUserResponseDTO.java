package com.org.checkr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateUserResponseDTO {
    private Long id;
    private String userName;
    private String email;
}
