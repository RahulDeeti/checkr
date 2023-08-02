package com.org.checkr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportDTO {
    private Long id;
    private String status;
    private String adjudication;
    private Date createdAt;
    private Date completedAt;
    private Date turnAroundTime;
}
