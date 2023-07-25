package com.org.checkr.controller;

import com.org.checkr.dto.CandidateDTO;
import com.org.checkr.entity.Candidate;
import com.org.checkr.service.CandidateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/candidates")
public class CandidatesController {
    @Autowired
    private CandidateService candidateService;

    @GetMapping("/{id}")
    public Candidate getCandidate(@PathVariable Long id) {
        Candidate candidate =  candidateService.getCandidateById(id).get();
        return candidate;
    }

    @GetMapping
    public List<CandidateDTO> getAllCandidates(@RequestParam(name = "name", required = false) String name,
                                               @RequestParam(name = "adjudication", required = false) String adjudication,
                                               @RequestParam(name = "status", required = false) String status) {
        List<Candidate> candidates = candidateService.getAllCandidates();
        List<CandidateDTO> candidateDTOS = candidates.stream()
                .map(candidate -> new CandidateDTO(
                        candidate.getName(),
                        candidate.getReport().getAdjudication(),
                        candidate.getReport().getStatus(),
                        candidate.getLocation(),
                        candidate.getReport().getCreatedAt()
                ))
                .collect(Collectors.toList());
        return candidateDTOS;
    }
    @PostMapping
    public Candidate createUser(@RequestBody Candidate candidate) {
        System.out.println(candidate.toString());
        return candidateService.saveCandidate(candidate);
    }
}
