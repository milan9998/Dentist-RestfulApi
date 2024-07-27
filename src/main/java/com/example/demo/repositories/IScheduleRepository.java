package com.example.demo.repositories;

import com.example.demo.entities.SchedulePatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.util.Date;
import java.util.List;

public interface IScheduleRepository extends JpaRepository<SchedulePatient,Integer> {
    void deleteById(Integer id);

    @Query(value = "SELECT * FROM scheduling_patients dr WHERE dr.appointment_date= :date ",nativeQuery = true)
    List<SchedulePatient> getAllSchedulingsByDate(Date date);

    @Query(value = "SELECT * FROM scheduling_patients WHERE appointment_date= :date AND appointment_time= :time AND dentist_id= :dentist_id",nativeQuery = true)
    List<SchedulePatient> getAllByDateTimeDentistId(Date date, Time time, Integer dentist_id);

    @Query(value = "SELECT * FROM scheduling_patients",nativeQuery = true)
    List<SchedulePatient> getAllAppointmentTime();
}
