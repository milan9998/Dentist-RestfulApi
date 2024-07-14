package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
public class CheckModel {
    private int dentist_id;
    private Date date;
    private Time time;

}
