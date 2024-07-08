package com.example.demo.repositories;

import com.example.demo.entities.SchedulePatient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IScheduleRepository extends JpaRepository<SchedulePatient,Integer> {
    void deleteById(Integer id);
}
