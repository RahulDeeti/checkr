package com.org.checkr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionnaireResponseDTO {
    private final Long id;
    private final String question;
}
