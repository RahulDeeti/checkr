package com.org.checkr.service;

import com.org.checkr.dto.request.AdverseActionRequestDTO;
import com.org.checkr.dto.response.AdverseActionResponseDTO;
import com.org.checkr.entity.AdverseAction;
import com.org.checkr.entity.Candidate;
import com.org.checkr.entity.Questionnaire;
import com.org.checkr.entity.User;
import com.org.checkr.repository.AdverseActionRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdverseActionService {

    private final AdverseActionRepository adverseActionRepository;
    private final UserService userService;
    private final CandidateService candidateService;
    private final QuestionnaireService questionnaireService;

    public AdverseActionService(AdverseActionRepository adverseActionRepository,
                                CandidateService candidateService,
                                UserService userService,
                                QuestionnaireService questionnaireService) {
        this.adverseActionRepository = adverseActionRepository;
        this.candidateService = candidateService;
        this.userService = userService;
        this.questionnaireService = questionnaireService;
    }

    public List<AdverseActionResponseDTO> getAllAdverseActions() {
        return adverseActionRepository.findAll().stream().map(adverseAction -> {
            return new AdverseActionResponseDTO(
                adverseAction.getUser().getId(),
                adverseAction.getCandidate().getId(),
                adverseAction.getStatus(),
                adverseAction.getPreAdverseActionNoticeDate(),
                adverseAction.getPostAdverseActionNoticeDate()
        );}).collect(Collectors.toList());
    }

    public AdverseAction saveAdverseAction(AdverseActionRequestDTO adverseActionDTO) {
       return adverseActionRepository.save(createAdverseActionFromDTO(adverseActionDTO));
    }

    private AdverseAction createAdverseActionFromDTO(AdverseActionRequestDTO adverseActionDTO) {
        Candidate candidate = candidateService.getCandidateById(adverseActionDTO.getCandidateId());
        User user = userService.getUserById(adverseActionDTO.getUserId());

        List<Long> questionnaireIds =  adverseActionDTO.getQuestionnaireIds();

        List<Questionnaire> questionnaires = new ArrayList<>();

        adverseActionDTO.getQuestionnaireIds().forEach(id ->
                questionnaires.add(questionnaireService.findQuestionnaireById(id))
        );

        candidate.setQuestionnaires(questionnaires);
        candidateService.saveCandidate(candidate);

        AdverseAction adverseAction = new AdverseAction();
        adverseAction.setCandidate(candidate);
        adverseAction.setStatus(adverseActionDTO.getStatus());
        adverseAction.setPreAdverseActionNoticeDate(adverseActionDTO.getPreAdverseActionNoticeDate());
        adverseAction.setPostAdverseActionNoticeDate(adverseActionDTO.getPostAdverseActionNoticeDate());
        adverseAction.setUser(user);
        adverseAction.setCandidate(candidate);

        return adverseAction;
    }

    public AdverseActionResponseDTO mapAdverseActionToDto(AdverseAction adverseAction) {
        return new AdverseActionResponseDTO(adverseAction.getUser().getId(),
                adverseAction.getCandidate().getId(),
                adverseAction.getStatus(),
                adverseAction.getPreAdverseActionNoticeDate(),
                adverseAction.getPostAdverseActionNoticeDate()
        );
    }
}

