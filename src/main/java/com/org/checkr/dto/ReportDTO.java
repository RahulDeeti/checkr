package com.org.checkr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDTO {
    private Long id;
    private String status;
    private String adjudication;
    private Date createdAt;
    private Date completedAt;
    private Date turnAroundTime;
}
