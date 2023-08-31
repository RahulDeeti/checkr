package com.org.checkr.controller;

import com.org.checkr.dto.CandidateDTO;
import com.org.checkr.dto.request.CreateCandidateRequestDTO;
import com.org.checkr.dto.response.CompleteCandidateInfoDTO;
import com.org.checkr.entity.Candidate;
import com.org.checkr.service.CandidateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidatesController {
    @Autowired
    private CandidateService candidateService;

    @GetMapping("/{id}")
    public ResponseEntity<CompleteCandidateInfoDTO> getCandidate(@PathVariable Long id) {
        Candidate candidate = candidateService.getCandidateById(id);
        CompleteCandidateInfoDTO candidateInfoDTO = candidateService.mapCandidateEntityToDto(candidate);
        return new ResponseEntity<>(candidateInfoDTO, HttpStatus.OK);
    }

    @GetMapping
    public List<CandidateDTO> getAllCandidates(@RequestParam(name = "name", required = false) String name,
                                               @RequestParam(name = "adjudication", required = false) String adjudication,
                                               @RequestParam(name = "status", required = false) String status) {
        return candidateService.getCandidatesByFilter(name, adjudication, status);

    }
    @PostMapping
    public ResponseEntity<CompleteCandidateInfoDTO> createCandidate(@RequestBody CreateCandidateRequestDTO candidateRequestDTO) {
        Candidate candidate = candidateService.createCandidate(candidateRequestDTO);
        CompleteCandidateInfoDTO responseDto = candidateService.mapCandidateEntityToDto(candidate);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CompleteCandidateInfoDTO> engageWithCandidate(@PathVariable Long id) {
        Candidate candidate = candidateService.engageWithCandidate(id);
        if (candidate == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        CompleteCandidateInfoDTO responseDto = candidateService.mapCandidateEntityToDto(candidate);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
