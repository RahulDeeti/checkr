package com.org.checkr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class CreateCandidateRequestDTO {
    private final String name;
    private final String email;
    private final Date dob;
    private final String phone;
    private final String zipcode;
    private final String socialSecurityCard;
    private final String drivingLicense;
    private final Date createdAt;
    private final String location;
}
