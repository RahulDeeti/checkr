package com.org.checkr.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AdverseActionDTO {
    private Long userId;
    private Long candidateId;
    private String status;
    private Date preAdverseActionNoticeDate;
    private Date postAdverseActionNoticeDate;

    public AdverseActionDTO() {
    }

    public AdverseActionDTO(Long userId, Long candidateId, String status, Date preAdverseActionNoticeDate, Date postAdverseActionNoticeDate) {
        this.userId = userId;
        this.candidateId = candidateId;
        this.status = status;
        this.preAdverseActionNoticeDate = preAdverseActionNoticeDate;
        this.postAdverseActionNoticeDate = postAdverseActionNoticeDate;
    }
}

