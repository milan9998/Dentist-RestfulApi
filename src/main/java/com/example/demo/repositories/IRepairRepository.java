package com.example.demo.repositories;

import com.example.demo.entities.DentalRepair;
import com.example.demo.models.RepairModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IRepairRepository extends JpaRepository<DentalRepair, Integer> {


    @Query(value = "SELECT * FROM dental_repairs dr WHERE dr.dentist_id = :dentist_id", nativeQuery = true)
    List<DentalRepair> getAllBydentist_id(Integer dentist_id);

}









