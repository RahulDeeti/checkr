package com.org.checkr.controller;

import com.org.checkr.dto.CandidateDTO;
import com.org.checkr.entity.Candidate;
import com.org.checkr.service.CandidateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidatesController {
    @Autowired
    private CandidateService candidateService;

    @GetMapping("/{id}")
    public Candidate getCandidate(@PathVariable Long id) {
        return candidateService.getCandidateById(id).get();
    }

    @GetMapping
    public List<CandidateDTO> getAllCandidates(@RequestParam(name = "name", required = false) String name,
                                               @RequestParam(name = "adjudication", required = false) String adjudication,
                                               @RequestParam(name = "status", required = false) String status) {
        return candidateService.getCandidatesByFilter(name, adjudication, status);

    }
    @PostMapping
    public Candidate createCandidate(@RequestBody Candidate candidate) {
        return candidateService.saveCandidate(candidate);
    }

    @PatchMapping("/{id}")
    public Candidate engageWithCandidate(@PathVariable Long id) {
        return candidateService.engageWithCandidate(id);
    }
}
