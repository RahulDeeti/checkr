package com.org.checkr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;
//import java.util.List;

@Getter
@AllArgsConstructor
@Data
public class AdverseActionRequestDTO {
    private final Long userId;
    private final Long candidateId;
    private final String status;
    private final Date preAdverseActionNoticeDate;
    private final Date postAdverseActionNoticeDate;
    private final ArrayList<Long> questionnaireIds;
}

