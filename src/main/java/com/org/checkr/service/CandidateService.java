package com.org.checkr.service;

import com.org.checkr.dto.CandidateDTO;
import com.org.checkr.dto.CourtSearchDTO;
import com.org.checkr.dto.ReportDTO;
import com.org.checkr.dto.request.CreateCandidateRequestDTO;
import com.org.checkr.dto.response.CompleteCandidateInfoDTO;
import com.org.checkr.entity.Candidate;
import com.org.checkr.entity.CourtSearch;
import com.org.checkr.entity.Report;
import com.org.checkr.exception.NotFoundException;
import com.org.checkr.repository.CandidateRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return mapToShortCandidateDTOS(candidateRepository.findAll());
    }

    public Candidate getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id).orElse(null);
        if(candidate == null) {
            throw new NotFoundException("Entity with ID " + id + " not found.");
        } else {
            return candidate;
        }
    }

    public Candidate saveCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public Candidate engageWithCandidate(Long id) {
        Candidate candidate = candidateRepository.findById(id).orElse(null);
        if(candidate == null) {
            throw new NotFoundException("Entity with ID " + id + " not found.");
        } else {
            Report report = candidate.getReport();
            Optional.ofNullable(report).ifPresent(r -> r.setAdjudication("Engaged"));
            candidate =  updateCandidate(candidate);
        }
        return candidate;
    }

    private Candidate updateCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public Candidate createCandidate(CreateCandidateRequestDTO requestDTO) {
        Candidate candidate = mapDtoToCandidateEntity(requestDTO);
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
            return mapToShortCandidateDTOS(typedQuery.getResultList());
        } else {
            // if there are no query params provided then return all candidates
            return getAllCandidates();
        }
    }

    private List<CandidateDTO> mapToShortCandidateDTOS(List<Candidate> candidates) {
        return candidates.stream()
                .map(candidate -> new CandidateDTO(
                        candidate.getName(),
                        Optional.ofNullable(candidate.getReport()).map(Report::getAdjudication).orElse(null),
                        Optional.ofNullable(candidate.getReport()).map(Report::getStatus).orElse(null),
                        candidate.getLocation(),
                        Optional.ofNullable(candidate.getReport()).map(Report::getCreatedAt).orElse(null)
                ))
                .collect(Collectors.toList());
    }

    private Candidate mapDtoToCandidateEntity(CreateCandidateRequestDTO requestDTO) {
        Candidate candidate = new Candidate();

        candidate.setName(requestDTO.getName());
        candidate.setEmail(requestDTO.getEmail());
        candidate.setDob(requestDTO.getDob());
        candidate.setPhone(requestDTO.getPhone());
        candidate.setZipcode(requestDTO.getZipcode());
        candidate.setSocialSecurityCard(requestDTO.getSocialSecurityCard());
        candidate.setDrivingLicense(requestDTO.getDrivingLicense());
        candidate.setCreatedAt(requestDTO.getCreatedAt());
        candidate.setLocation(requestDTO.getLocation());

        return candidate;
    }

    public CompleteCandidateInfoDTO mapCandidateEntityToDto(Candidate candidate) {
        ReportDTO reportDTO = null;
        List<CourtSearchDTO> courtSearchDTOs = null;
        if(candidate.getReport() != null) {
            Report report = candidate.getReport();
            reportDTO = new ReportDTO(
                    report.getId(),
                    report.getStatus(),
                    report.getAdjudication(),
                    report.getCreatedAt(),
                    report.getCompletedAt(),
                    report.getTurnAroundTime()
            );
        }
        if(candidate.getCourtSearches() != null) {
            courtSearchDTOs = candidate.getCourtSearches().stream().map(
                    courtSearch -> new CourtSearchDTO(courtSearch.getId(), courtSearch.getSearch())
            ).collect(Collectors.toList());
        }
        return new CompleteCandidateInfoDTO(
                candidate.getId(),
                candidate.getName(),
                candidate.getEmail(),
                candidate.getDob(),
                candidate.getPhone(),
                candidate.getZipcode(),
                candidate.getSocialSecurityCard(),
                candidate.getDrivingLicense(),
                candidate.getCreatedAt(),
                candidate.getLocation(),
                reportDTO,
                courtSearchDTOs
        );
    }

}
