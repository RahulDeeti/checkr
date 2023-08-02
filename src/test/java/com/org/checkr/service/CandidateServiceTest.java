package com.org.checkr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.javafaker.Faker;

import com.org.checkr.dto.CandidateDTO;
import com.org.checkr.dto.request.CreateCandidateRequestDTO;
import com.org.checkr.dto.response.CompleteCandidateInfoDTO;
import com.org.checkr.entity.Candidate;
import com.org.checkr.entity.CourtSearch;
import com.org.checkr.entity.Report;
import com.org.checkr.exception.NotFoundException;
import com.org.checkr.repository.CandidateRepository;

import jakarta.persistence.criteria.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    private CandidateService candidateService;
    private CandidateRepository candidateRepository;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private CriteriaBuilder criteriaBuilder;

    @MockBean
    private CriteriaQuery<Candidate> criteriaQuery;

    @MockBean
    private Root<Candidate> root;

    @MockBean
    private TypedQuery<Candidate> typedQuery;

    private final Faker faker = new Faker();
    @BeforeEach
    public void setUp() {
        candidateRepository = mock(CandidateRepository.class);
        candidateService = new CandidateService(candidateRepository);
    }

    @Test
    @DisplayName("Get All Candidates - Should Return List of CandidateDTOs")
    void testGetAllCandidates_ShouldReturnListOfCandidateDTOs() {
        Candidate candidate1 = createCandidate(1L);
        Candidate candidate2 = createCandidate(2L);

        List<Candidate> candidates = List.of(candidate1, candidate2);
        when(candidateRepository.findAll()).thenReturn(candidates);

        List<CandidateDTO> result = candidateService.getAllCandidates();

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Filter Candidates - Should Return List of CandidateDTOs For No Query params")
    void testGetCandidatesByFilter_ShouldReturnListOfCandidateDTOs() {
        Candidate candidate1 = createCandidate(1L);
        Candidate candidate2 = createCandidate(2L);

        List<Candidate> candidates = List.of(candidate1, candidate2);
        when(candidateRepository.findAll()).thenReturn(candidates);

        List<CandidateDTO> result = candidateService.getCandidatesByFilter(null, null, null);

        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Get Candidate By Existing ID - Should Return Candidate")
    void testGetCandidateById_ExistingId_ReturnsCandidate() {
        Long candidateId = 1L;
        Candidate candidate = createCandidate(1L);
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        Candidate result = candidateService.getCandidateById(candidateId);
        
        assertEquals(candidate, result);
        assertEquals(candidate.getId(), result.getId());
    }

    @Test
    @DisplayName("Get Candidate By Non-Existing ID - Should Throw NotFoundException")
    void testGetCandidateById_NonExistingId_ThrowsNotFoundException() {
        Long nonExistingId = 2L;
        when(candidateRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> candidateService.getCandidateById(nonExistingId));
    }

    @Test
    @DisplayName("Create Candidate - Should Save and Return New Candidate")
    void testCreateCandidate_ShouldSaveAndReturnNewCandidate() {
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

    @Test
    void testEngageWithCandidate_ValidId_ReturnsEngagedCandidate() {
        Long candidateId = 1L;
        Candidate candidate = new Candidate();
        candidate.setId(candidateId);
        candidate.setName("John Doe");

        Report report = new Report();
        report.setId(101L);
        report.setAdjudication("Pending");

        candidate.setReport(report);

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(candidateRepository.save(candidate)).thenReturn(candidate);

        Candidate engagedCandidate = candidateService.engageWithCandidate(candidateId);

        assertEquals(candidateId, engagedCandidate.getId());
        assertEquals("Engaged", engagedCandidate.getReport().getAdjudication());

        verify(candidateRepository, times(1)).findById(candidateId);
        verify(candidateRepository, times(1)).save(candidate);
    }

    @Test
    void testSaveCandidate() {
        Candidate candidateToSave = new Candidate();
        candidateToSave.setName("John Doe");

        Candidate savedCandidate = new Candidate();
        savedCandidate.setId(1L);
        savedCandidate.setName("John Doe");

        when(candidateRepository.save(candidateToSave)).thenReturn(savedCandidate);

        Candidate result = candidateService.saveCandidate(candidateToSave);

        assertEquals(savedCandidate, result);
        verify(candidateRepository, times(1)).save(candidateToSave);
    }


    @Test
    void testEngageWithCandidate_InValidCandidateId_ThrowsNotFoundException() {
        Long candidateId = 1L;
        Candidate candidate = new Candidate();
        candidate.setId(candidateId);
        candidate.setName("John Doe");

        assertThrows(NotFoundException.class, () -> candidateService.engageWithCandidate(candidateId));

    }

    @Test
    @DisplayName("Map Candidate Entity to DTO - Should Return CompleteCandidateInfoDTO")
    void testMapCandidateEntityToDto() {
        Candidate candidate = new Candidate();
        candidate.setId(1L);
        candidate.setName("John Doe");
        candidate.setEmail("john.doe@example.com");
        candidate.setDob(new Date());
        candidate.setPhone("1234567890");
        candidate.setZipcode("12345");
        candidate.setSocialSecurityCard("123-45-6789");
        candidate.setDrivingLicense("ABC123");
        candidate.setCreatedAt(new Date());
        candidate.setLocation("Location XYZ");

        Report report = new Report();
        report.setId(101L);
        report.setStatus("Completed");
        report.setAdjudication("Engaged");
        report.setCreatedAt(new Date());
        report.setCompletedAt(new Date());
        report.setTurnAroundTime(new Date());

        candidate.setReport(report);

        CourtSearch courtSearch1 = new CourtSearch();
        courtSearch1.setId(201L);
        courtSearch1.setSearch("Search 1");

        CourtSearch courtSearch2 = new CourtSearch();
        courtSearch2.setId(202L);
        courtSearch2.setSearch("Search 2");

        candidate.setCourtSearches(List.of(courtSearch1, courtSearch2));

        CompleteCandidateInfoDTO dto = candidateService.mapCandidateEntityToDto(candidate);

        assertEquals(candidate.getId(), dto.getId());
        assertEquals(candidate.getName(), dto.getName());
        assertEquals(candidate.getEmail(), dto.getEmail());
        assertEquals(candidate.getDob(), dto.getDob());
        assertEquals(candidate.getPhone(), dto.getPhone());
        assertEquals(candidate.getZipcode(), dto.getZipcode());
        assertEquals(candidate.getSocialSecurityCard(), dto.getSocialSecurityCard());
        assertEquals(candidate.getDrivingLicense(), dto.getDrivingLicense());
        assertEquals(candidate.getCreatedAt(), dto.getCreatedAt());
        assertEquals(candidate.getLocation(), dto.getLocation());

        assertNotNull(dto.getReport());
        assertEquals(report.getId(), dto.getReport().getId());
        assertEquals(report.getStatus(), dto.getReport().getStatus());
        assertEquals(report.getAdjudication(), dto.getReport().getAdjudication());
        assertEquals(report.getCreatedAt(), dto.getReport().getCreatedAt());
        assertEquals(report.getCompletedAt(), dto.getReport().getCompletedAt());
        assertEquals(report.getTurnAroundTime(), dto.getReport().getTurnAroundTime());

        assertNotNull(dto.getCourtSearches());
        assertEquals(2, dto.getCourtSearches().size());
        assertEquals(courtSearch1.getId(), dto.getCourtSearches().get(0).getId());
        assertEquals(courtSearch2.getId(), dto.getCourtSearches().get(1).getId());
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

