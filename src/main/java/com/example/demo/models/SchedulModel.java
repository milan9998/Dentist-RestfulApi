package com.example.demo.models;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
public class SchedulModel {
    private Integer user_id;
    private Integer dentist_id;
    private String first_name;
    private String last_name;
    private String email;
    private String contact_number;
    private Date date;
    private Time time;
}
