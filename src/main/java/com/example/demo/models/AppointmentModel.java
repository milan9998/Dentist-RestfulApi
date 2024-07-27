package com.example.demo.models;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;

@Data
@Builder
public class AppointmentModel {
    private Time time;
}
