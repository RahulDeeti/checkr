package com.org.checkr.dto.response;

import com.org.checkr.dto.CourtSearchDTO;
import com.org.checkr.dto.ReportDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CompleteCandidateInfoDTO {
    private Long id;
    private String name;
    private String email;
    private Date dob;
    private String phone;
    private String zipcode;
    private String socialSecurityCard;
    private String drivingLicense;
    private Date createdAt;
    private String location;
    private ReportDTO report;
    private List<CourtSearchDTO> courtSearches;
}
