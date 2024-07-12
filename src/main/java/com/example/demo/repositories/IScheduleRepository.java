package com.example.demo.repositories;

import com.example.demo.entities.SchedulePatient;
import com.example.demo.models.DateModel;
import com.example.demo.models.SchedulModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IScheduleRepository extends JpaRepository<SchedulePatient,Integer> {
    void deleteById(Integer id);

    @Query(value = "SELECT * FROM scheduling_patients dr WHERE dr.appointment_date= :date ",nativeQuery = true)
    List<SchedulePatient> getAllSchedulingsByDate(Date date);
}
