package com.org.checkr.service;

import com.org.checkr.dto.AdverseActionDTO;
import com.org.checkr.entity.AdverseAction;
import com.org.checkr.entity.Candidate;
import com.org.checkr.repository.AdverseActionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class AdverseActionService {

    private final AdverseActionRepository adverseActionRepository;
    private final UserService userService;
    private final CandidateService candidateService;

    @Autowired
    public AdverseActionService(AdverseActionRepository adverseActionRepository,
                                CandidateService candidateService,
                                UserService userService) {
        this.adverseActionRepository = adverseActionRepository;
        this.candidateService = candidateService;
        this.userService = userService;
    }

    public List<AdverseActionDTO> getAllAdverseActions() {
        return adverseActionRepository.findAll().stream().map(adverseAction -> {
            return new AdverseActionDTO(
                adverseAction.getUser().getId(),
                adverseAction.getCandidate().getId(),
                adverseAction.getStatus(),
                adverseAction.getPreAdverseActionNoticeDate(),
                adverseAction.getPostAdverseActionNoticeDate()
        );}).collect(Collectors.toList());
    }

    public AdverseAction saveAdverseAction(AdverseActionDTO adverseActionDTO) {
        return adverseActionRepository.save(createAdverseActionFromDTO(adverseActionDTO));
    }

    private AdverseAction createAdverseActionFromDTO(AdverseActionDTO adverseActionDTO) {
        Candidate candidate = candidateService.getCandidateById(adverseActionDTO.getCandidateId()).get();

        AdverseAction adverseAction = new AdverseAction();
        adverseAction.setCandidate(candidate);
        adverseAction.setStatus(adverseActionDTO.getStatus());
        adverseAction.setPreAdverseActionNoticeDate(adverseActionDTO.getPreAdverseActionNoticeDate());
        adverseAction.setUser(userService.getUserById(adverseActionDTO.getUserId()).get());

        return adverseAction;
    }
}

