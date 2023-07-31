package com.org.checkr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;

import com.org.checkr.dto.CandidateDTO;
import com.org.checkr.dto.request.CreateCandidateRequestDTO;
import com.org.checkr.entity.Candidate;
import com.org.checkr.entity.Report;
import com.org.checkr.exception.NotFoundException;
import com.org.checkr.repository.CandidateRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class CandidateServiceTest {

    private CandidateService candidateService;
    private CandidateRepository candidateRepository;

    private final Faker faker = new Faker();
    @BeforeEach
    public void setUp() {
        candidateRepository = mock(CandidateRepository.class);
        candidateService = new CandidateService(candidateRepository);
    }

    @Test
    @DisplayName("Get All Candidates - Should Return List of CandidateDTOs")
    public void testGetAllCandidates_ShouldReturnListOfCandidateDTOs() {
        Candidate candidate1 = createCandidate(1L);
        Candidate candidate2 = createCandidate(2L);

        List<Candidate> candidates = List.of(candidate1, candidate2);
        when(candidateRepository.findAll()).thenReturn(candidates);

        List<CandidateDTO> result = candidateService.getAllCandidates();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Get Candidate By Existing ID - Should Return Candidate")
    public void testGetCandidateById_ExistingId_ReturnsCandidate() {
        Long candidateId = 1L;
        Candidate candidate = createCandidate(1L);
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        Candidate result = candidateService.getCandidateById(candidateId);
        
        assertEquals(candidate, result);
        assertEquals(candidate.getId(), result.getId());
    }

    @Test
    @DisplayName("Get Candidate By Non-Existing ID - Should Throw NotFoundException")
    public void testGetCandidateById_NonExistingId_ThrowsNotFoundException() {
        Long nonExistingId = 2L;
        when(candidateRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> candidateService.getCandidateById(nonExistingId));
    }

    @Test
    @DisplayName("Create Candidate - Should Save and Return New Candidate")
    public void testCreateCandidate_ShouldSaveAndReturnNewCandidate() {
        CreateCandidateRequestDTO requestDTO = new CreateCandidateRequestDTO(
                "John Doe",
                "john.doe@example.com",
                new Date(),
                "1234567890",
                "12345",
                "123-45-6789",
                "ABC123",
                new Date(),
                "Location XYZ"
        );

        Candidate candidate = createCandidate(1L);
        when(candidateRepository.save(any(Candidate.class))).thenReturn(candidate);

        Candidate result = candidateService.createCandidate(requestDTO);

        assertEquals(candidate.getName(), result.getName());
        assertEquals(candidate.getEmail(), result.getEmail());
    }

    private Candidate createCandidate(Long id) {
        Candidate candidate = new Candidate();

        candidate.setId(id);
        candidate.setName(faker.name().fullName());
        candidate.setEmail(faker.internet().emailAddress());
        candidate.setDob(faker.date().birthday());
        candidate.setPhone(faker.phoneNumber().phoneNumber());
        candidate.setZipcode(faker.address().zipCode());
        candidate.setSocialSecurityCard(faker.numerify("###-##-####"));
        candidate.setDrivingLicense(faker.bothify("???###"));

        Report report = new Report();
        report.setStatus(faker.lorem().word());
        report.setAdjudication(faker.lorem().word());
        report.setTotalPackage(faker.number().numberBetween(1, 100));
        report.setCreatedAt(faker.date().past(30, TimeUnit.DAYS));
        report.setCompletedAt(faker.date().future(30, TimeUnit.DAYS));

        candidate.setReport(report);

        return candidate;
    }

}

