package com.org.checkr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdverseActionResponseDTO {
    private Long userId;
    private Long candidateId;
    private String status;
    private Date preAdverseActionNoticeDate;
    private Date postAdverseActionNoticeDate;
}
