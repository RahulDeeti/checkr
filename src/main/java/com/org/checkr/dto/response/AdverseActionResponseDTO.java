package com.org.checkr.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class AdverseActionResponseDTO {
    private final Long userId;
    private final Long candidateId;
    private final String status;
    private final Date preAdverseActionNoticeDate;
    private final Date postAdverseActionNoticeDate;
}
