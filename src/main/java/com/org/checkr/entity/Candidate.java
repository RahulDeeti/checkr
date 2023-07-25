package com.org.checkr.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "candidate")
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Temporal(TemporalType.DATE)
    private Date dob;

    private String phone;

    private String zipcode;

    @Column(name = "social_security_card")
    private String socialSecurityCard;

    @Column(name = "driving_license")
    private String drivingLicense;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(nullable = false)
    private String location;

    @OneToOne(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Report report;

}

