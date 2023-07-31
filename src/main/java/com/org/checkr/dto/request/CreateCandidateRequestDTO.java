package com.org.checkr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCandidateRequestDTO {
    private String name;
    private String email;
    private Date dob;
    private String phone;
    private String zipcode;
    private String socialSecurityCard;
    private String drivingLicense;
    private Date createdAt;
    private String location;

}
