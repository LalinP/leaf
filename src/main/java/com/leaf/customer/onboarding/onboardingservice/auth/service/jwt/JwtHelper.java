package com.leaf.customer.onboarding.onboardingservice.auth.service.jwt;

import java.security.Key;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtHelper {
  private static final Key SECRET_KEY =  Keys.secretKeyFor(SignatureAlgorithm.HS256);
  private static final int DURATION_MINUTES = 30;

  public  String generateJwt(String userId, String permission, int expMinutes, ChronoUnit units) {
   return Jwts.builder()
        .subject(userId)
        .id(UUID.randomUUID().toString())
        .issuedAt(Date.from(Instant.now()))
        .expiration(Date.from(Instant.now().plus(expMinutes, units)))
        .claim("permission",permission)
        .claim("organisation", "Leaf")
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)

        .compact();
  }

  public String getUserId(String token) {
    return getTokenBody(token).getSubject();
  }

  public boolean isTokenExpired(String token) {
    return getTokenBody(token)
        .getExpiration()
        .before(new Date());
  }

  private Claims getTokenBody(String token) {
    try{
      return Jwts.parser()
          .setSigningKey(SECRET_KEY)
          .build()
          .parseSignedClaims(token)
          .getPayload();

    }catch( ExpiredJwtException exception) {
      throw new AccessDeniedException(exception.getMessage());
    }
  }

  public  Boolean validateToken(String token, UserDetails userDetails) {
    final String userId = getUserId(token);
    return userId.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }


}
