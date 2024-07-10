package com.example.demo.repositories;

import com.example.demo.entities.Dentist;
import com.example.demo.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
      select t from Token t inner join Dentist u\s
      on t.dentist.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);
    Optional<Token> findByRefreshToken(String refreshToken);
    Optional<Token> findTokenByDentist(Dentist dentist);
}
