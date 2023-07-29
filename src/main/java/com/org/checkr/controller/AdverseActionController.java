package com.org.checkr.controller;

import com.org.checkr.dto.request.AdverseActionRequestDTO;
import com.org.checkr.dto.response.AdverseActionResponseDTO;
import com.org.checkr.entity.AdverseAction;
import com.org.checkr.service.AdverseActionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AdverseActionResponseDTO> createAdverseAction(@RequestBody AdverseActionRequestDTO adverseActionDTO,
                                                                        @PathVariable(name = "candidate-id") Long candidateId) {
        AdverseAction adverseAction =  adverseActionService.saveAdverseAction(adverseActionDTO);
        return new ResponseEntity<>(adverseActionService.mapAdverseActionToDto(adverseAction), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AdverseActionResponseDTO>> getAllAdverseActions() {
        return new ResponseEntity<>(adverseActionService.getAllAdverseActions(), HttpStatus.OK);
    }
}

