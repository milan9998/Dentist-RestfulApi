package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.demo.enums.TokenTypeEnum;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tokens")
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token")
    private String token;

    @Column(name = "refresh_token")
    public String refreshToken;

    @Enumerated(EnumType.STRING)
    public TokenTypeEnum tokenType = TokenTypeEnum.BEARER;


    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dentist_id")
    public Dentist dentist;
}
