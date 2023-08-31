package com.org.checkr.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CandidateDTO {

    private String name;
    private String adjudication;
    private String status;
    private String location;
    private Date date;


    public CandidateDTO() {
    }

    public CandidateDTO(String name, String adjudication, String status, String location, Date date) {
        this.name = name;
        this.adjudication = adjudication;
        this.status = status;
        this.location = location;
        this.date = date;
    }
}
