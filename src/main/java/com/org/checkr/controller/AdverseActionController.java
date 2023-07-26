package com.org.checkr.controller;

import com.org.checkr.dto.AdverseActionDTO;
import com.org.checkr.entity.AdverseAction;
import com.org.checkr.service.AdverseActionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adverse-actions")
public class AdverseActionController {

    private final AdverseActionService adverseActionService;

    @Autowired
    public AdverseActionController(AdverseActionService adverseActionService) {
        this.adverseActionService = adverseActionService;
    }

    @PostMapping("/{candidate-id}")
    public AdverseAction createAdverseAction(@RequestBody AdverseActionDTO adverseActionDTO,
                                             @PathVariable(name = "candidate-id") Long candidateId) {
        adverseActionDTO.setCandidateId(candidateId);
        return adverseActionService.saveAdverseAction(adverseActionDTO);
    }

    @GetMapping
    public List<AdverseActionDTO> getAllAdverseActions() {
        return  adverseActionService.getAllAdverseActions();
    }
}

