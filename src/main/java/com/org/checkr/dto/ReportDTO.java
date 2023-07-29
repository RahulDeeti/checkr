package com.org.checkr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@AllArgsConstructor
public class ReportDTO {
    private final Long id;
    private final String status;
    private final String adjudication;
    private final Date createdAt;
    private final Date completedAt;
    private final Date turnAroundTime;
}
