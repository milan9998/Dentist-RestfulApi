package com.example.demo.repositories;

import com.example.demo.entities.DentalRepair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepairRepository extends JpaRepository<DentalRepair, Integer> {

}
