package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
@Builder
public class CheckModel {
    private int dentist_id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private Time time;

}
