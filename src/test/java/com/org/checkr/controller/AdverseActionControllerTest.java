package com.org.checkr.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.checkr.dto.request.AdverseActionRequestDTO;
import com.org.checkr.dto.response.AdverseActionResponseDTO;
import com.org.checkr.entity.AdverseAction;
import com.org.checkr.service.AdverseActionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdverseActionControllerTest {

    @Mock
    private AdverseActionService adverseActionService;

    @InjectMocks
    private AdverseActionController adverseActionController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adverseActionController).build();
    }

    @Test
    void testCreateAdverseAction() throws Exception {
        Long userId = 123L;
        Long candidateId = 456L;
        String status = "clear";
        Date preAdverseActionNoticeDate = new Date();
        Date postAdverseActionNoticeDate = new Date();
        ArrayList<Long> questionnaireIds = new ArrayList<>();

        AdverseActionRequestDTO requestDTO = new AdverseActionRequestDTO(
                userId,
                candidateId,
                status,
                preAdverseActionNoticeDate,
                postAdverseActionNoticeDate,
                questionnaireIds
        );


        AdverseAction adverseAction = new AdverseAction();

        AdverseActionResponseDTO responseDTO = new AdverseActionResponseDTO(
                userId,
                candidateId,
                status,
                preAdverseActionNoticeDate,
                postAdverseActionNoticeDate
        );

        when(adverseActionService.saveAdverseAction(any())).thenReturn(adverseAction);
        when(adverseActionService.mapAdverseActionToDto(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/adverse-actions/{candidate-id}", 123L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(responseDTO.getUserId()))
                .andExpect(jsonPath("$.candidateId").value(responseDTO.getCandidateId()))
                .andExpect(jsonPath("$.status").value(responseDTO.getStatus()));

        verify(adverseActionService, times(1)).saveAdverseAction(any());
        verify(adverseActionService, times(1)).mapAdverseActionToDto(any());
    }

    @Test
    void testGetAllAdverseActions() throws Exception {
        List<AdverseActionResponseDTO> responseDTOList = new ArrayList<>();

        Long userId = 123L;
        Long candidateId = 456L;
        String status = "clear";
        Date preAdverseActionNoticeDate = new Date();
        Date postAdverseActionNoticeDate = new Date();
        ArrayList<Long> questionnaireIds = new ArrayList<>();

        AdverseActionResponseDTO responseDTO = new AdverseActionResponseDTO(
                userId,
                candidateId,
                status,
                preAdverseActionNoticeDate,
                postAdverseActionNoticeDate
        );

        responseDTOList.add(responseDTO);
        when(adverseActionService.getAllAdverseActions()).thenReturn(responseDTOList);

        mockMvc.perform(get("/api/adverse-actions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].candidateId").value(responseDTOList.get(0).getCandidateId())) // Adjust this based on your actual DTO fields
                .andExpect(jsonPath("$[0].userId").value(responseDTOList.get(0).getUserId()));

        verify(adverseActionService, times(1)).getAllAdverseActions();
    }

    private String asJsonString(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

