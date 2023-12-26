package com.leaf.customer.onboarding.onboardingservice.auth.service.jwt;

import java.security.Key;
import java.security.SignatureException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.access.AccessDeniedException;

public class JwtHelper {
  private static final Key SECRET_KEY =  Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private static final int DURATION_MINUTES = 30;

  public static String generateJwt(String userId, String permission) {
   return Jwts.builder()
        .id(UUID.randomUUID().toString())
        .issuedAt(Date.from(Instant.now()))
        .expiration(Date.from(Instant.now().plus(DURATION_MINUTES, ChronoUnit.MINUTES)))
        .claim("permission",permission)
        .claim("user_id", userId)
        .claim("organisation", "Leaf")
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
  }

  public static String getUserId(String token) {

  }

  private static Claims getTokenBody(String token) {
    try{
      return Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .build()
          .parseSignedClaims(token)
          .getPayload();

    }catch(SignatureException | ExpiredJwtException exception) {
      throw new AccessDeniedException(exception.getMessage());
    }
  }


}
