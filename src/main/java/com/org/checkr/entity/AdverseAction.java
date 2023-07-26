package com.org.checkr.entity;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "adverse_action")
public class AdverseAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "pre_adverse_action_notice_date", nullable = false)
    private Date preAdverseActionNoticeDate;

    @Column(name = "post_adverse_action_notice_date")
    private Date postAdverseActionNoticeDate;
}

