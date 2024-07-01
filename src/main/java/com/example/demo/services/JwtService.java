package com.example.demo.services;

import com.example.demo.entities.Dentist;
import com.example.demo.entities.Token;
import com.example.demo.entities.User;
import com.example.demo.enums.TokenTypeEnum;
import com.example.demo.repositories.ITokenRepository;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;
    @Value("${security.jwt.refresh.expiration-time}")
    private long jwtRefreshToken;


    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateToken(Map<String, Object> extraClaims, String jwtSubject) {
        return buildToken(extraClaims, jwtSubject, jwtExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            String jwtSubject,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(jwtSubject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(Dentist dentist) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", dentist.getId());
        claims.put("email", dentist.getEmail());
        claims.put("first_name", dentist.getFirst_name());
        claims.put("last_name", dentist.getLast_name());
        claims.put("contact_number", dentist.getContact_number());
        claims.put("roles", dentist.getAuthorities());

        return generateToken(claims, dentist.getEmail());
    }

    public String generateRefreshToken(Dentist dentistDetails) {
        return buildToken(new HashMap<>(), dentistDetails.getEmail(), jwtRefreshToken);
    }



    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
