package com.org.checkr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "court_search")
public class CourtSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String search;

    @ManyToMany(mappedBy = "courtSearches")
    @JsonIgnore
    private List<Candidate> candidateList;
}

