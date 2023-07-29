package com.org.checkr.dto.response;

import com.org.checkr.dto.CourtSearchDTO;
import com.org.checkr.dto.ReportDTO;
import com.org.checkr.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class CompleteCandidateInfoDTO {
    private final Long id;
    private final String name;
    private final String email;
    private final Date dob;
    private final String phone;
    private final String zipcode;
    private final String socialSecurityCard;
    private final String drivingLicense;
    private final Date createdAt;
    private final String location;
    private final ReportDTO report;
    private final List<CourtSearchDTO> courtSearches; // change it to court searches dto
}
