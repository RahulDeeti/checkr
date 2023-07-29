package com.org.checkr.controller;

import com.org.checkr.dto.response.CompleteCandidateInfoDTO;
import com.org.checkr.dto.response.QuestionnaireResponseDTO;
import com.org.checkr.entity.Questionnaire;
import com.org.checkr.service.QuestionnaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questionnaires")
public class QuestionnairesController {

    private final QuestionnaireService questionnaireService;

    public QuestionnairesController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionnaireResponseDTO>> getAllQuestionnaires() {
        List<QuestionnaireResponseDTO> infoDtos =  questionnaireService.getAllQuestionnaires().stream()
                .map(questionnaireService::mapQuestionnaireToDto).toList();
        return new ResponseEntity<>(infoDtos, HttpStatus.OK);
    }
}

