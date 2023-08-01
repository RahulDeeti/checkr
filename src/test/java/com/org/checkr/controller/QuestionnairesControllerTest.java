package com.org.checkr.controller;

import com.org.checkr.entity.Questionnaire;
import com.org.checkr.service.QuestionnaireService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class QuestionnairesControllerTest {

    @Mock
    private QuestionnaireService questionnaireService;

    @InjectMocks
    private QuestionnairesController questionnairesController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(questionnairesController).build();
    }

    @Test
    public void testGetAllQuestionnaires() throws Exception {
        List<Questionnaire> questionnaires = new ArrayList<>();

        Questionnaire questionnaire1 = new Questionnaire();
        questionnaire1.setId(1L);
        Questionnaire questionnaire2 = new Questionnaire();
        questionnaire2.setId(2L);

        questionnaires.add(questionnaire1);
        questionnaires.add(questionnaire2);

        when(questionnaireService.getAllQuestionnaires()).thenReturn(questionnaires);

        mockMvc.perform(get("/api/questionnaires"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(questionnaires.size()));

        for (Questionnaire questionnaire : questionnaires) {
            verify(questionnaireService).mapQuestionnaireToDto(questionnaire);
        }
    }
}
