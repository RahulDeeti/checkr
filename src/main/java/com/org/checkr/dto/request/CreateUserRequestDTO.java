package com.org.checkr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Data
public class CreateUserRequestDTO {
    private String userName;
    private String email;
    private String password;
}
