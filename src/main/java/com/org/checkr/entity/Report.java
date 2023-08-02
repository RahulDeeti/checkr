package com.org.checkr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String status;

    @Column
    private String adjudication;

    @Column(name = "package")
    private Integer totalPackage;

    @Column(name = "created_at", columnDefinition = "DATE DEFAULT CURRENT_DATE")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column
    @Temporal(TemporalType.DATE)
    private Date completedAt;

    @Column(name = "turn_around_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date turnAroundTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    @JsonIgnore
    private Candidate candidate;
}
