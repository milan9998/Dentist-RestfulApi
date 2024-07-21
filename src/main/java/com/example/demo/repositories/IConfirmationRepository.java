package com.example.demo.repositories;

import com.example.demo.entities.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IConfirmationRepository extends JpaRepository<ConfirmationToken,Integer> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

}
