package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
@Entity
@Table(name="scheduling_patients")
public class SchedulePatient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="user_id")
    private Integer user_id;
    @Column(name="dentist_id")
    private Integer dentist_id;
    @Column(name="appointment_date")
    private Date date;
    @Column(name="appointment_time")
    private Time time;

    /*
    user_id int,
dentist_id int,
appointment_date date,
appointment_time time,
    */


}
