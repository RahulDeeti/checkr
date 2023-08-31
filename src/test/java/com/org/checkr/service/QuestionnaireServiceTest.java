package com.org.checkr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.org.checkr.dto.response.QuestionnaireResponseDTO;
import com.org.checkr.entity.Questionnaire;
import com.org.checkr.exception.NotFoundException;
import com.org.checkr.repository.QuestionnaireRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class QuestionnaireServiceTest {

    private QuestionnaireService questionnaireService;
    private QuestionnaireRepository questionnaireRepository;

    @BeforeEach
    void setUp() {
        questionnaireRepository = mock(QuestionnaireRepository.class);
        questionnaireService = new QuestionnaireService(questionnaireRepository);
    }

    @Test
    @DisplayName("Find Existing Questionnaire By ID - Should Return Questionnaire")
    void testFindQuestionnaireById_ExistingId_ReturnsQuestionnaire() {
        Long questionnaireId = 1L;
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(questionnaireId);
        questionnaire.setQuestion("What is the reason?");
        when(questionnaireRepository.findById(questionnaireId)).thenReturn(Optional.of(questionnaire));

        Questionnaire result = questionnaireService.findQuestionnaireById(questionnaireId);

        assertNotNull(result);
        assertEquals(questionnaire, result);
    }

    @Test
    @DisplayName("Find Non-Existing Questionnaire By ID - Should Throw NotFoundException")
    void testFindQuestionnaireById_NonExistingId_ThrowsNotFoundException() {
        Long nonExistingId = 2L;
        when(questionnaireRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> questionnaireService.findQuestionnaireById(nonExistingId));
    }

    @Test
    @DisplayName("Map Questionnaire to DTO - Should Return DTO with Correct Fields")
    void testMapQuestionnaireToDto_ReturnsCorrectDto() {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(1L);
        questionnaire.setQuestion("What is your favorite color?");

        QuestionnaireResponseDTO result = questionnaireService.mapQuestionnaireToDto(questionnaire);

        assertNotNull(result);
        assertEquals(questionnaire.getId(), result.getId());
        assertEquals(questionnaire.getQuestion(), result.getQuestion());
    }

    @Test
    @DisplayName("Get All Questionnaires - Should Return List of Questionnaires")
    void testGetAllQuestionnaires_ShouldReturnListOfQuestionnaires() {
        Questionnaire questionnaire1 = new Questionnaire();
        questionnaire1.setId(1L);
        questionnaire1.setQuestion("What is the reason for this?");

        Questionnaire questionnaire2 = new Questionnaire();
        questionnaire1.setId(2L);
        questionnaire1.setQuestion("How often do you do this?");

        List<Questionnaire> questionnaires = List.of(
            questionnaire1, questionnaire2
        );
        when(questionnaireRepository.findAll()).thenReturn(questionnaires);

        List<Questionnaire> result = questionnaireService.getAllQuestionnaires();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}

