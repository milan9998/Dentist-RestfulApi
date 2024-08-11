package com.example.demo.repositories;

import com.example.demo.entities.Dentist;
import com.example.demo.models.RepairModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IDentistRepository extends JpaRepository<Dentist, Integer> {
    Optional findByEmail(String email);
    RepairModel findById(int id);

    Boolean existsByEmail(String email);
    Dentist findByEmailIgnoreCase(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE dentists SET password = :password WHERE email = :email", nativeQuery = true)
    void resetPassword(String email, String password);
}
