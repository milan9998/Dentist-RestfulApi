package com.example.demo.repositories;

import com.example.demo.entities.Dentist;
import com.example.demo.models.RepairModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDentistRepository extends JpaRepository<Dentist, Integer> {
    Optional findByEmail(String email);
    RepairModel findById(int id);
}
