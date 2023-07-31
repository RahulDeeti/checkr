package com.org.checkr.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.org.checkr.dto.request.AdverseActionRequestDTO;
import com.org.checkr.dto.response.AdverseActionResponseDTO;
import com.org.checkr.entity.AdverseAction;
import com.org.checkr.entity.Candidate;
import com.org.checkr.entity.Questionnaire;
import com.org.checkr.entity.User;
import com.org.checkr.repository.AdverseActionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class AdverseActionServiceTest {

    private AdverseActionService adverseActionService;
    private AdverseActionRepository adverseActionRepository;
    private CandidateService candidateService;
    private UserService userService;
    private QuestionnaireService questionnaireService;

    @BeforeEach
    void setUp() {
        adverseActionRepository = mock(AdverseActionRepository.class);
        candidateService = mock(CandidateService.class);
        userService = mock(UserService.class);
        questionnaireService = mock(QuestionnaireService.class);
        adverseActionService = new AdverseActionService(
                adverseActionRepository,
                candidateService,
                userService,
                questionnaireService
        );
    }

    @Test
    @DisplayName("Save Adverse Action - Should Return Saved AdverseAction")
    void testSaveAdverseAction_ReturnsSavedAdverseAction() {
        Long userId = 1L;
        Long candidateId = 2L;
        Long questionnaireId = 1L;
        String status = "In Progress";
        Date preAdverseActionDate = new Date();
        Date postAdverseActionDate = new Date();
        ArrayList<Long> questinnaireIds = new ArrayList<>();
        questinnaireIds.add(1L);

        AdverseActionRequestDTO adverseActionDTO = new AdverseActionRequestDTO(
                userId,
                candidateId,
                status,
                preAdverseActionDate,
                postAdverseActionDate,
                questinnaireIds
        );

        Candidate candidate = new Candidate();
        candidate.setId(candidateId);

        User user = new User();
        user.setId(userId);

        AdverseAction adverseAction = createAdverseAction(user, candidate, status, preAdverseActionDate,
                postAdverseActionDate);

        when(candidateService.getCandidateById(candidateId)).thenReturn(candidate);
        when(userService.getUserById(userId)).thenReturn(user);
        when(questionnaireService.findQuestionnaireById(questionnaireId))
                .thenReturn(new Questionnaire());

        when(adverseActionRepository.save(any(AdverseAction.class))).thenReturn(adverseAction);

        AdverseAction result = adverseActionService.saveAdverseAction(adverseActionDTO);

        assertNotNull(result);
        assertEquals(candidate, result.getCandidate());
        assertEquals(status, result.getStatus());
        assertEquals(preAdverseActionDate, result.getPreAdverseActionNoticeDate());
        assertEquals(postAdverseActionDate, result.getPostAdverseActionNoticeDate());
        assertEquals(user, result.getUser());
    }

    @Test
    @DisplayName("Get All Adverse Actions - Should Return List of Adverse Actions")
    void testGetAllAdverseActions_ShouldReturnListOfAdverseActions() {

        List<AdverseAction> adverseActions = new ArrayList<>();

        Candidate candidate = new Candidate();
        candidate.setId(4L);
        User user = new User();
        user.setId(5L);
        AdverseAction adverseAction1 = createAdverseAction(user, candidate, "Completed", new Date(), new Date());
        adverseActions.add(adverseAction1);


        Candidate candidate2 = new Candidate();
        candidate2.setId(3L);
        User user2 = new User();
        user2.setId(4L);

        AdverseAction adverseAction2 = createAdverseAction(user, candidate, "In Progress", new Date(), new Date());
        adverseActions.add(adverseAction2);

        when(adverseActionRepository.findAll()).thenReturn(adverseActions);

        List<AdverseActionResponseDTO> result = adverseActionService.getAllAdverseActions();

        assertNotNull(result);
        assertEquals(2, result.size());

        AdverseActionResponseDTO responseDTO1 = result.get(0);
        assertEquals(4L, responseDTO1.getCandidateId());
        assertEquals(5L, responseDTO1.getUserId());
        assertEquals("Completed", responseDTO1.getStatus());


        AdverseActionResponseDTO responseDTO2 = result.get(1);
        assertNotEquals(3L, responseDTO2.getCandidateId());
        assertNotEquals(4L, responseDTO2.getUserId());
        assertEquals("In Progress", responseDTO2.getStatus());

    }

    @Test
    @DisplayName("Map Adverse Action to DTO - Should Return DTO with Correct Fields")
    void testMapAdverseActionToDto_ReturnsCorrectDto() {
        Long userId = 1L;
        Long candidateId = 2L;
        String status = "Completed";
        Date preAdverseActionDate = new Date();
        Date postAdverseActionDate = new Date();

        Candidate candidate = new Candidate();
        candidate.setId(candidateId);

        User user = new User();
        user.setId(userId);

        AdverseAction adverseAction = createAdverseAction(user, candidate, status, preAdverseActionDate,
                postAdverseActionDate);

        AdverseActionResponseDTO result = adverseActionService.mapAdverseActionToDto(adverseAction);

        assertNotNull(result);
        assertEquals(candidateId, result.getCandidateId());
        assertEquals(userId, result.getUserId());
        assertEquals(status, result.getStatus());
        assertEquals(preAdverseActionDate, result.getPreAdverseActionNoticeDate());
        assertEquals(postAdverseActionDate, result.getPostAdverseActionNoticeDate());
    }

    private AdverseAction createAdverseAction(
            User user,
            Candidate candidate,
            String status,
            Date preAdverseActionDate,
            Date postAdverseActionDate
    ) {

        AdverseAction adverseAction = new AdverseAction();
        adverseAction.setCandidate(candidate);
        adverseAction.setUser(user);
        adverseAction.setStatus(status);
        adverseAction.setPreAdverseActionNoticeDate(preAdverseActionDate);
        adverseAction.setPostAdverseActionNoticeDate(postAdverseActionDate);

        return adverseAction;
    }
}

