package com.org.checkr.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
//import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdverseActionRequestDTO {
    private Long userId;
    private Long candidateId;
    private String status;
    private Date preAdverseActionNoticeDate;
    private Date postAdverseActionNoticeDate;
    private ArrayList<Long> questionnaireIds;
}

