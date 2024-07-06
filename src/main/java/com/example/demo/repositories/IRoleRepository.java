package com.example.demo.repositories;

import com.example.demo.entities.DentistRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<DentistRoles, Integer> {
}
