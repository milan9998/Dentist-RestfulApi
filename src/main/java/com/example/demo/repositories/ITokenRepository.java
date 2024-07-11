package com.example.demo.repositories;

import com.example.demo.entities.Token;
import com.example.demo.models.LogoutRequestModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token, Integer> {


    Optional<Token> findByToken(String token);
    Optional<Token> findByRefreshToken(String refreshToken);
    //Optional<Token> findTokenByDentist(Dentist dentist);

   /* @Query(value = "INSERT INTO tokens (revoked,expired) VALUES(1,1)  " +
            "WHERE dentist_id = :dentist_id", nativeQuery = true)*/

    @Modifying
    @Transactional
    @Query(value = "UPDATE tokens SET revoked = true, expired = true WHERE dentist_id = :dentist_id",nativeQuery = true)
    int revokeTokens(Integer dentist_id);

   /* @Query(value ="DELETE  from tokens dr WHERE dr.dentist_id= :dentist_id ",nativeQuery = true)
    void deleteBydentist_id(Integer dentist_id);*/



}
