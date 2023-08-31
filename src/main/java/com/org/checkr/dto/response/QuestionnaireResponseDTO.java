package com.org.checkr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionnaireResponseDTO {
    private Long id;
    private String question;
}
