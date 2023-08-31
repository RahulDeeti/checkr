package com.org.checkr.service;

import com.org.checkr.dto.CandidateDTO;
import com.org.checkr.entity.Candidate;
import com.org.checkr.repository.CandidateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidateService {
    @PersistenceContext
    private EntityManager entityManager;
    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<CandidateDTO> getAllCandidates() {
        return convertToCandidateDTOS(candidateRepository.findAll());
    }

    public Optional<Candidate> getCandidateById(Long id) {
        System.out.println(candidateRepository.findById(id));
        return candidateRepository.findById(id);
    }

    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }


    public List<CandidateDTO> getCandidatesByFilter(String name, String adjudication, String status) {

        // check if any of the query parameters is provided
        if (name != null || adjudication != null || status != null) {
            // prepare the search criteria based on the provided parameters
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Candidate> query = cb.createQuery(Candidate.class);
            Root<Candidate> root = query.from(Candidate.class);
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (adjudication != null) {
                predicates.add(cb.equal(cb.lower(root.get("report").get("adjudication")), adjudication.toLowerCase()));
            }

            if (status != null) {
                predicates.add(cb.equal(cb.lower(root.get("report").get("status")), status.toLowerCase()));
            }

            query.where(predicates.toArray(new Predicate[0]));

            TypedQuery<Candidate> typedQuery = entityManager.createQuery(query);
            return convertToCandidateDTOS(typedQuery.getResultList());
        } else {
            // if there are no query params provided then return all candidates
            return getAllCandidates();
        }
    }

    private List<CandidateDTO> convertToCandidateDTOS(List<Candidate> candidates) {
        return candidates.stream()
                .map(candidate -> new CandidateDTO(
                        candidate.getName(),
                        candidate.getReport().getAdjudication(),
                        candidate.getReport().getStatus(),
                        candidate.getLocation(),
                        candidate.getReport().getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

}
