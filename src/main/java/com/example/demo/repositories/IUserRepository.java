package com.example.demo.repositories;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional findByEmail(String email);
    //Optional findByEmail(String email);
    Boolean findById(int id);
    void deleteById(int id);
}
