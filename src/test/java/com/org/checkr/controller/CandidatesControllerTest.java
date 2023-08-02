package com.org.checkr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.checkr.dto.CandidateDTO;
import com.org.checkr.dto.request.CreateCandidateRequestDTO;
import com.org.checkr.dto.response.CompleteCandidateInfoDTO;
import com.org.checkr.entity.Candidate;
import com.org.checkr.service.CandidateService;

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
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class CandidatesControllerTest {

    @Mock
    private CandidateService candidateService;

    @InjectMocks
    private CandidatesController candidatesController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(candidatesController).build();
    }

    @Test
    void testGetCandidateById() throws Exception {
        Long candidateId = 1L;
        String name = "john";
        String email = "john@email.com";
        Date dob = new Date();
        String phone = "12345";
        String zipcode = "4321";
        String socialSecurityCard = "sc2324234";
        String drivingLicence = "d32423423";
        Date createdAt = new Date();
        String location = "Hawai";

        Candidate candidate = new Candidate();
        candidate.setId(candidateId);

        CompleteCandidateInfoDTO candidateInfoDTO = new CompleteCandidateInfoDTO(candidateId, name, email, dob, phone,
                zipcode, socialSecurityCard, drivingLicence, createdAt, location, null, null);


        when(candidateService.getCandidateById(candidateId)).thenReturn(candidate);
        when(candidateService.mapCandidateEntityToDto(candidate)).thenReturn(candidateInfoDTO);

        mockMvc.perform(get("/api/candidates/{id}", candidateId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(candidateId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.dob").value(dob))
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.zipcode").value(zipcode))
                .andExpect(jsonPath("$.socialSecurityCard").value(socialSecurityCard))
                .andExpect(jsonPath("$.drivingLicense").value(drivingLicence))
                .andExpect(jsonPath("$.location").value(location));

        verify(candidateService).getCandidateById(candidateId);
        verify(candidateService).mapCandidateEntityToDto(candidate);
    }

    @Test
    void testGetAllCandidates() throws Exception {
        List<CandidateDTO> candidateDTOs = new ArrayList<>();

        CandidateDTO candidateDTO1 = new CandidateDTO("john ross", "adverse action", "clear",
                "Hawai", new Date());
        CandidateDTO candidateDTO2 = new CandidateDTO("john cena", "In review", "clear",
                "LA", new Date());
        candidateDTOs.add(candidateDTO1);
        candidateDTOs.add(candidateDTO2);


        when(candidateService.getCandidatesByFilter("john", null, null)).thenReturn(candidateDTOs);

        mockMvc.perform(get("/api/candidates?name=john"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("john ross"))
                .andExpect(jsonPath("$[0].adjudication").value("adverse action"))
                .andExpect(jsonPath("$[1].name").value("john cena"))
                .andExpect(jsonPath("$[1].adjudication").value("In review"));

        verify(candidateService).getCandidatesByFilter("john", null, null);
    }

    @Test
    void testCreateCandidate() throws Exception {
        Long candidateId = 1L;
        String name = "john";
        String email = "john@email.com";
        Date dob = new Date();
        String phone = "12345";
        String zipcode = "4321";
        String socialSecurityCard = "sc2324234";
        String drivingLicence = "d32423423";
        Date createdAt = new Date();
        String location = "Hawai";

        CreateCandidateRequestDTO requestDTO = new CreateCandidateRequestDTO(name, email, dob, phone, zipcode,
                socialSecurityCard, drivingLicence, createdAt, location);

        Candidate candidate = new Candidate();
        candidate.setId(candidateId);

        CompleteCandidateInfoDTO responseDTO = new CompleteCandidateInfoDTO(candidateId, name, email, dob, phone,
                zipcode, socialSecurityCard, drivingLicence, createdAt, location, null, null);

        when(candidateService.createCandidate(any(CreateCandidateRequestDTO.class))).thenReturn(candidate);
        when(candidateService.mapCandidateEntityToDto(candidate)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(candidateId))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.email").value(email));

        verify(candidateService).createCandidate(requestDTO);
        verify(candidateService).mapCandidateEntityToDto(candidate);
    }

    @Test
    void testEngageWithCandidate() throws Exception {
        long candidateId = 1L;
        Candidate candidate = new Candidate();
        candidate.setId(candidateId);
        CompleteCandidateInfoDTO responseDTO = new CompleteCandidateInfoDTO();

        when(candidateService.engageWithCandidate(candidateId)).thenReturn(candidate);
        when(candidateService.mapCandidateEntityToDto(candidate)).thenReturn(responseDTO);

        mockMvc.perform(patch("/api/candidates/{id}", candidateId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(candidateService).engageWithCandidate(candidateId);
        verify(candidateService).mapCandidateEntityToDto(candidate);
    }

    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

